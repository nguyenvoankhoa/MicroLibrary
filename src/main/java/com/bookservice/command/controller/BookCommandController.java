package com.bookservice.command.controller;

import com.bookservice.command.command.CreateBookCommand;
import com.bookservice.command.command.DeleteBookCommand;
import com.bookservice.command.command.UpdateBookCommand;
import com.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookCommandController {
    private final CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookRequestModel requestModel) {
        CreateBookCommand command = new CreateBookCommand
                (UUID.randomUUID().toString(), requestModel.getName(), requestModel.getAuthor(), true);
        commandGateway.sendAndWait(command);
        return new ResponseEntity<>(command, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody BookRequestModel requestModel) {
        UpdateBookCommand command = new UpdateBookCommand
                (requestModel.getBookId(), requestModel.getName(), requestModel.getAuthor(), requestModel.getIsReady());
        commandGateway.sendAndWait(command);
        return new ResponseEntity<>(command, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable String bookId) {
        DeleteBookCommand command = new DeleteBookCommand(bookId);
        commandGateway.sendAndWait(command);
        return new ResponseEntity<>(command, HttpStatus.ACCEPTED);
    }

}
