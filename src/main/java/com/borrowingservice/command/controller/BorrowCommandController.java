package com.borrowingservice.command.controller;

import com.borrowingservice.command.command.CreateBorrowCommand;
import com.borrowingservice.command.command.UpdateBookReturnCommand;
import com.borrowingservice.command.model.BorrowRequestModel;
import com.borrowingservice.command.service.BorrowingService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/borrowing")
public class BorrowCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private BorrowingService borrowService;

    @PostMapping
    public ResponseEntity<?> addBorrowing(@RequestBody BorrowRequestModel model) {
        try {
            CreateBorrowCommand command = new CreateBorrowCommand(UUID.randomUUID().toString(), model.getBookId(), model.getEmployeeId(), new Date());
            commandGateway.sendAndWait(command);

        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> updateBorrowing(@RequestBody BorrowRequestModel model) {
        try {
            UpdateBookReturnCommand command = new UpdateBookReturnCommand(borrowService.findIdBorrowing(
                    model.getEmployeeId(),
                    model.getBookId()),
                    model.getBookId(),
                    model.getEmployeeId(),
                    new Date());
            commandGateway.sendAndWait(command);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
