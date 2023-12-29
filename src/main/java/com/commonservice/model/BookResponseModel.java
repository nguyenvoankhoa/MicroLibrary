package com.commonservice.model;

import lombok.Data;

@Data
public class BookResponseModel {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
