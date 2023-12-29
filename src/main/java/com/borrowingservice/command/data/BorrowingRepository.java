package com.borrowingservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, String> {
    List<Borrowing> findByEmployeeIdAndReturnDateIsNull(String employeeId);
    Borrowing findByEmployeeIdAndBookIdAndReturnDateIsNull(String employeeId,String bookId);
}
