package com.loggerproject.coreservice.server.data.document.file.extra.metadata;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Metadata {
    String type;
    String name;
    String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date created;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date lastUpdated;
}
