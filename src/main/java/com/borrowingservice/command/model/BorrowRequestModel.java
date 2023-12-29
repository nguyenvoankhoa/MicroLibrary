package com.borrowingservice.command.model;

import lombok.Data;

import java.util.Date;
@Data
public class BorrowRequestModel {
    private String id;

    private String bookId;

    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;
}
