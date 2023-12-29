package com.borrowingservice.command.event;

import lombok.Data;

import java.util.Date;

@Data
public class BookReturnUpdatedEvent {
    private String id;
    private String bookId;
    private String employee;
    private Date returnDate;
}
