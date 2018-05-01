package com.loggerproject.coreservice.server.service.filedata.afiledata.get.model;

import lombok.Data;

@Data
public class PageSizeOverflowException extends Exception {
    public PageSizeOverflowException(String message) {
        super(message);
    }
}
