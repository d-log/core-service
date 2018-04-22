package com.loggerproject.coreservice.global.server.document.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MetaData {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date created;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date lastUpdated;

    /**
     * This default constructor is needed for fasterxml to recreate the MetaData object from DB
     */
    public MetaData() {
    }

    public MetaData(Date created, Date lastUpdated) {
        this.created = created;
        this.lastUpdated = lastUpdated;
    }
}
