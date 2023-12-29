package com.borrowingservice.command.saga;

import com.borrowingservice.command.command.DeleteBorrowCommand;
import com.borrowingservice.command.command.SendMessageCommand;
import com.borrowingservice.command.event.BorrowCreatedEvent;
import com.borrowingservice.command.event.BorrowDeletedEvent;
import com.borrowingservice.command.event.BorrowSendMessageEvent;
import com.commonservice.command.RollBackBookStatusCommand;
import com.commonservice.command.UpdateBookStatusCommand;
import com.commonservice.event.BookRollBackStatusEvent;
import com.commonservice.event.BookUpdateStatusEvent;
import com.commonservice.model.BookResponseModel;
import com.commonservice.model.EmployeeResponseModel;
import com.commonservice.query.GetBookDetail;
import com.commonservice.query.GetEmployeeDetail;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event) {
        System.out.println("BorrowCreatedEvent in Saga for BookId : " + event.getBookId() + " : EmployeeId :  " + event.getEmployeeId());
        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());
            GetBookDetail queryDetail = new GetBookDetail(event.getBookId());
            BookResponseModel model = queryGateway.query(
                    queryDetail, ResponseTypes.instanceOf(BookResponseModel.class)).join();
            if (model.getIsReady()) {
                UpdateBookStatusCommand updateBookStatusCommand = new UpdateBookStatusCommand(
                        event.getBookId(), false, event.getEmployeeId(), event.getId());
                commandGateway.sendAndWait(updateBookStatusCommand);
            } else {
                throw new Exception("Book has been borrowed!");
            }
        } catch (Exception e) {
            cancelBorrow(event.getId());
        }
    }

    private void cancelBorrow(String id) {
        DeleteBorrowCommand command = new DeleteBorrowCommand(id);
        commandGateway.sendAndWait(command);
    }


    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event) {
        System.out.println("BookUpdateStatusEvent in Saga for BookId : " + event.getBookId());
        try {
            GetEmployeeDetail employeeDetail = new GetEmployeeDetail(event.getEmployeeId());
            EmployeeResponseModel employee = queryGateway.query(
                    employeeDetail, ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
            if (!employee.getIsDisciplined()) {
                SendMessageCommand command = new SendMessageCommand(
                        event.getBorrowId(), employee.getEmployeeId(), "Borrow book success");
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("Employee is disciplined!");
            }
        } catch (Exception e) {
            cancelBookUpdateStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowId());
        }
    }

    private void cancelBookUpdateStatus(String bookId, String employeeId, String borrowId) {
        System.out.println("Cancel rollback update book status");
        RollBackBookStatusCommand command = new RollBackBookStatusCommand(bookId, true, employeeId, borrowId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookRollBackStatusEvent rollBackStatusEvent) {
        System.out.println("Rollback event for borrow id : " + rollBackStatusEvent.getBorrowId());
        cancelBorrow(rollBackStatusEvent.getBorrowId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(BorrowSendMessageEvent event) {
        System.out.println("Borrow complete : " + event.getId());
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(BorrowDeletedEvent event) {
        System.out.println("Borrow has been deleted : " + event.getId());
    }
}
