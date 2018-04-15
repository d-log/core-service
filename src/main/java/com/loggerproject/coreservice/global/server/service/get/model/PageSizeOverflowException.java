package com.loggerproject.coreservice.global.server.service.get.model;

import lombok.Data;

@Data
public class PageSizeOverflowException extends Exception {
    public PageSizeOverflowException(String message) {
        super(message);
    }
}
