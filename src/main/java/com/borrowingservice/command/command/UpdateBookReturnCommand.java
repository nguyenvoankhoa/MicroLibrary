package com.borrowingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
@Data
@AllArgsConstructor
public class UpdateBookReturnCommand {
    @TargetAggregateIdentifier
    private String id;
    private String bookId;
    private String employee;
    private Date returnDate;
}
