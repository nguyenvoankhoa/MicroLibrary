package com.borrowingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class DeleteBorrowCommand {
    @TargetAggregateIdentifier
    private String id;

}
