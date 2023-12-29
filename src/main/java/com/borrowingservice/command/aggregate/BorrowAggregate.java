package com.borrowingservice.command.aggregate;

import com.borrowingservice.command.command.CreateBorrowCommand;
import com.borrowingservice.command.command.DeleteBorrowCommand;
import com.borrowingservice.command.command.SendMessageCommand;
import com.borrowingservice.command.command.UpdateBookReturnCommand;
import com.borrowingservice.command.event.BookReturnUpdatedEvent;
import com.borrowingservice.command.event.BorrowCreatedEvent;
import com.borrowingservice.command.event.BorrowDeletedEvent;
import com.borrowingservice.command.event.BorrowSendMessageEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class BorrowAggregate {
    @AggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

    private String message;

    public BorrowAggregate() {
    }

    @CommandHandler
    public BorrowAggregate(CreateBorrowCommand command) {
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreatedEvent event) {
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();
    }

    @CommandHandler
    public void handle(UpdateBookReturnCommand command) {
        BookReturnUpdatedEvent event = new BookReturnUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookReturnUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployee();
        this.returnDate = event.getReturnDate();
    }

    @CommandHandler
    public void handle(DeleteBorrowCommand command) {
        BorrowDeletedEvent event = new BorrowDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowDeletedEvent event) {
        this.id = event.getId();
    }


    @CommandHandler
    public void handle(SendMessageCommand command) {
        BorrowSendMessageEvent event = new BorrowSendMessageEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowSendMessageEvent event) {
        this.id = event.getId();
        this.employeeId = event.getEmployeeId();
        this.message = event.getMessage();
    }

}
