package com.loggerproject.coreservice.server.data.document.log;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.log.extra.LogTypes;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(collection = "log")
public class LogModel extends GlobalModel {

    @Id
    String ID;

    String title;
    String description;

    // required
    List<LogData> logDatas; // size must be >= 1
    Set<String> directoryIDs; // size must be >= 1

    // Optional
    Set<String> tagIDs; // if null, instantiate empty collection

    // if null, instantiate with default
    LogTypes logTypes;
}
