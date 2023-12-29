package com.employeeservice.command.controller;

import com.employeeservice.command.command.CreateEmployeeCommand;
import com.employeeservice.command.command.DeleteEmployeeCommand;
import com.employeeservice.command.command.UpdateEmployeeCommand;
import com.employeeservice.command.model.EmployeeRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/employees")
@EnableBinding(Source.class)
public class EmployeeController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private MessageChannel output;


    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand
                (UUID.randomUUID().toString(), model.getFirstName(), model.getLastName(), model.getKin(), false);
        commandGateway.sendAndWait(command);
        return new ResponseEntity<>(command, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequestModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand
                (model.getEmployeeId(), model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined());
        commandGateway.sendAndWait(command);
        return new ResponseEntity<>(command, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@RequestBody EmployeeRequestModel model) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(model.getEmployeeId());
        commandGateway.sendAndWait(command);
        return new ResponseEntity<>(command, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            System.out.println(json);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

}
