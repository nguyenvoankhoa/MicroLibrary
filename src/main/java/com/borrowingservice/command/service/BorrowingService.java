package com.borrowingservice.command.service;

import com.borrowingservice.command.data.BorrowingRepository;
import com.borrowingservice.command.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(Source.class)
public class BorrowingService {
    @Autowired
    private MessageChannel output;
    @Autowired
    private BorrowingRepository repository;

    public void sendMessage(Message message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(message);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

    public String findIdBorrowing(String employeeId, String bookId) {

        return repository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId).getId();
    }
}
