package com.bookservice.query.controller;

import com.bookservice.query.model.BookResponseModel;
import com.bookservice.query.query.GetAllBooks;
import com.bookservice.query.query.GetBookById;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookQueryController {
    private final QueryGateway queryGateway;

    public BookQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        GetAllBooks getAllBooks = new GetAllBooks();
        List<BookResponseModel> responses = queryGateway.query(getAllBooks, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable String bookId) {
        GetBookById getBookById = new GetBookById();
        getBookById.setBookId(bookId);
        BookResponseModel model = queryGateway.query(getBookById, ResponseTypes.instanceOf(BookResponseModel.class)).join();
        if (model != null) {
            return new ResponseEntity<>(model, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
