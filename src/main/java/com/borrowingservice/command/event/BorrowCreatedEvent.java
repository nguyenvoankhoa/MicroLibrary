package com.borrowingservice.command.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BorrowCreatedEvent {
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
}
