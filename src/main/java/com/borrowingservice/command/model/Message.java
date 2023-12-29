package com.borrowingservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String employeeId;
    private String message;
}
