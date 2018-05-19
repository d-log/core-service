package com.loggerproject.coreservice.data.model._shared;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Metadata {
    String name;
    String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date created;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date lastUpdated;
}
