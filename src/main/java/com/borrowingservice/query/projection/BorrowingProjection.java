package com.borrowingservice.query.projection;

import com.borrowingservice.command.data.Borrowing;
import com.borrowingservice.command.data.BorrowingRepository;
import com.borrowingservice.query.model.BorrowingResponseModel;
import com.borrowingservice.query.query.GetAllBorrowing;
import com.borrowingservice.query.query.GetBorrowListByEmployee;
import com.commonservice.model.BookResponseModel;
import com.commonservice.model.EmployeeResponseModel;
import com.commonservice.query.GetBookDetail;
import com.commonservice.query.GetEmployeeDetail;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingProjection {
    @Autowired
    private BorrowingRepository repository;
    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetAllBorrowing query) {
        List<Borrowing> borrowingList = repository.findAll();
        List<BorrowingResponseModel> responses = new ArrayList<>();
        borrowingList.forEach(borrowing -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(borrowing, model);
            BookResponseModel bookResponseModel = queryGateway.query(
                    new GetBookDetail(model.getBookId()), ResponseTypes.instanceOf(BookResponseModel.class)).join();
            EmployeeResponseModel employeeResponseModel = queryGateway.query(
                    new GetEmployeeDetail(model.getEmployeeId()), ResponseTypes.instanceOf(EmployeeResponseModel.class)
            ).join();
            model.setNameBook(bookResponseModel.getName());
            model.setNameEmployee(employeeResponseModel.getFirstName() + " " + employeeResponseModel.getLastName());
            responses.add(model);
        });
        return responses;
    }

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetBorrowListByEmployee query) {
        List<BorrowingResponseModel> list = new ArrayList<>();
        List<Borrowing> listEntity = repository.findAll();
        listEntity.forEach(s -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(s, model);
            model.setNameBook(queryGateway.query(new GetBookDetail(model.getBookId()), ResponseTypes.instanceOf(BookResponseModel.class)).join().getName());
            EmployeeResponseModel employee = queryGateway.query(new GetEmployeeDetail(model.getEmployeeId()), ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
            model.setNameEmployee(employee.getFirstName() + " " + employee.getLastName());
            list.add(model);
        });
        return list;
    }
}
