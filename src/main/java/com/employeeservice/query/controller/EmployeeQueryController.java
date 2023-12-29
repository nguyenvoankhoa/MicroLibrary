package com.employeeservice.query.controller;

import com.employeeservice.query.model.EmployeeResponseModel;
import com.employeeservice.query.query.GetAllEmployees;
import com.employeeservice.query.query.GetEmployeeById;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeQueryController {
    private final QueryGateway queryGateway;

    public EmployeeQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        GetAllEmployees getAllEmployees = new GetAllEmployees();
        List<EmployeeResponseModel> responseModels = queryGateway
                .query(getAllEmployees, ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
        return new ResponseEntity<>(responseModels, HttpStatus.OK);
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String employeeId) {
        GetEmployeeById getEmployeeById = new GetEmployeeById();
        getEmployeeById.setEmployeeId(employeeId);
        EmployeeResponseModel response = queryGateway
                .query(getEmployeeById, ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
