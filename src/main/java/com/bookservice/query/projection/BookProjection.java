package com.bookservice.query.projection;

import com.bookservice.command.data.Book;
import com.bookservice.command.data.BookRepository;
import com.bookservice.command.model.BookRequestModel;
import com.bookservice.query.model.BookResponseModel;
import com.bookservice.query.query.GetAllBooks;
import com.bookservice.query.query.GetBookById;
import com.commonservice.query.GetBookDetail;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    private final BookRepository bookRepository;

    public BookProjection(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookById query) {
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.findById(query.getBookId()).orElse(null);
        if (book != null) {
            BeanUtils.copyProperties(book, model);
            return model;
        }
        return null;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooks query) {
        List<Book> listBook = bookRepository.findAll();
        List<BookResponseModel> responses = new ArrayList<>();
        listBook.forEach(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book, model);
            responses.add(model);
        });
        return responses;
    }

    @QueryHandler
    public com.commonservice.model.BookResponseModel handle(GetBookDetail bookDetail) {
        com.commonservice.model.BookResponseModel model = new com.commonservice.model.BookResponseModel();
        Book book = bookRepository.getById(bookDetail.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }
}
