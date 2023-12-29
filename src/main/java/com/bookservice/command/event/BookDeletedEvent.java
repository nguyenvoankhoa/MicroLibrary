package com.bookservice.command.event;

import lombok.Data;

@Data
public class BookDeletedEvent {
    private String bookId;
}
