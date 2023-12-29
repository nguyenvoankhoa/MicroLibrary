package com.employeeservice.command.event;

import com.employeeservice.command.data.Employee;
import com.employeeservice.command.data.EmployeeRepository;
import com.employeeservice.command.model.EmployeeRequestModel;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeEventsHandler {
    private final EmployeeRepository employeeRepository;

    public EmployeeEventsHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @EventHandler
    public void handle(EmployeeCreatedEvent event) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        employeeRepository.save(employee);
    }
    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        Employee employee = employeeRepository.getById(event.getEmployeeId());
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDisciplined(event.getIsDisciplined());
        employeeRepository.save(employee);
    }
    @EventHandler
    public void on(EmployeeDeletedEvent event) {
        try {
            employeeRepository.deleteById(event.getEmployeeId());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }
}
