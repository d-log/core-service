package com.loggerproject.coreservice.server;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "log")
public class LogModel {
    ALogData logData;
}
