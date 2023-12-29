package com.employeeservice.command.model;

import lombok.Data;

@Data
public class EmployeeRequestModel {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

}
