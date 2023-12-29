package com.borrowingservice.command.event;

import com.borrowingservice.command.data.Borrowing;
import com.borrowingservice.command.data.BorrowingRepository;
import com.borrowingservice.command.model.Message;
import com.borrowingservice.command.service.BorrowingService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BorrowEventsHandler {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BorrowingService borrowingService;

    @EventHandler
    public void on(BorrowCreatedEvent event) {
        Borrowing borrowing = new Borrowing();
        BeanUtils.copyProperties(event, borrowing);
        borrowingRepository.save(borrowing);
    }

    @EventHandler
    public void on(BorrowDeletedEvent event) {
        if (borrowingRepository.findById(event.getId()).isPresent()) {
            borrowingRepository.deleteById(event.getId());
        }
    }

    @EventHandler
    public void on(BorrowSendMessageEvent event) {
        Message message = new Message(event.getEmployeeId(), event.getMessage());
        borrowingService.sendMessage(message);
    }

    @EventHandler
    public void on(BookReturnUpdatedEvent event) {
        Borrowing model = borrowingRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(event.getEmployee(), event.getBookId());
        model.setReturnDate(event.getReturnDate());
        borrowingRepository.save(model);
    }
}
