package com.employeeservice.command.event;

import lombok.Data;

@Data
public class EmployeeDeletedEvent {
    private String employeeId;
}
