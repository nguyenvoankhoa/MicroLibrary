package com.commonservice.event;

import lombok.Data;

@Data
public class BookRollBackStatusEvent {
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;
}
