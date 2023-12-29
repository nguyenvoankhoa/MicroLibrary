package com.borrowingservice.query.controller;

import com.borrowingservice.query.model.BorrowingResponseModel;
import com.borrowingservice.query.query.GetAllBorrowing;
import com.borrowingservice.query.query.GetBorrowListByEmployee;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/borrowing")
public class BorrowingQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getBorrowingByEmployee(@PathVariable String employeeId) {
        GetBorrowListByEmployee getBorrowingQuery = new GetBorrowListByEmployee(employeeId);

        List<BorrowingResponseModel> list =
                queryGateway.query(getBorrowingQuery, ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class))
                        .join();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllBorrowing() {
        List<BorrowingResponseModel> list = queryGateway.query(new GetAllBorrowing(), ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class))
                .join();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
