package com.bookservice.command.event;

import lombok.Data;

@Data
public class BookUpdatedEvent {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
