package com.employeeservice.query.projection;

import com.commonservice.query.GetEmployeeDetail;
import com.employeeservice.command.data.Employee;
import com.employeeservice.command.data.EmployeeRepository;
import com.employeeservice.query.model.EmployeeResponseModel;
import com.employeeservice.query.query.GetAllEmployees;
import com.employeeservice.query.query.GetEmployeeById;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {
    private final EmployeeRepository employeeRepository;

    public EmployeeProjection(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeById query) {
        Employee employee = employeeRepository.findById(query.getEmployeeId()).orElse(null);
        if (employee != null) {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, model);
            return model;
        }
        return null;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployees query) {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponseModel> responses = new ArrayList<>();
        employeeList.forEach(employee -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, model);
            responses.add(model);
        });
        return responses;
    }

    @QueryHandler
    public com.commonservice.model.EmployeeResponseModel handle(GetEmployeeDetail employeeDetail) {
        com.commonservice.model.EmployeeResponseModel model = new com.commonservice.model.EmployeeResponseModel();
        Employee employee = employeeRepository.getById(employeeDetail.getEmployeeId());
        BeanUtils.copyProperties(employee, model);
        return model;
    }
}
