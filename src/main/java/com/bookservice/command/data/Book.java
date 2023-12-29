package com.bookservice.command.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

}
