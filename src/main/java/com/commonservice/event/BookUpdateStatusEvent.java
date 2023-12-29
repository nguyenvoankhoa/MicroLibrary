package com.commonservice.event;

import lombok.Data;

@Data
public class BookUpdateStatusEvent {
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;
}
