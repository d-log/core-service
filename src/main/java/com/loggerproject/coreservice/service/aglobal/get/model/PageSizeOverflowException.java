package com.loggerproject.coreservice.service.aglobal.get.model;

import lombok.Data;

@Data
public class PageSizeOverflowException extends Exception {
    public PageSizeOverflowException(String message) {
        super(message);
    }
}
