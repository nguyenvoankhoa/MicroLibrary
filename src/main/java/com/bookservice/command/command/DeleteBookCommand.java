package com.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Data
@AllArgsConstructor
public class DeleteBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
}
