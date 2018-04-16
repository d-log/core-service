package com.loggerproject.coreservice.server.data.document.log;

import com.loggerproject.coreservice.server.data.document.log.extra.LogTypes;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.service.data.log.get.ALogModelTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.LogType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * ALogTypeModel mirrors this class, hence when updating this class please make the necessary changes there as well
 */
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "log")
public class LogModel extends ALogModelTypeModel {
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
    // when creating/updating log only set anything inside LogTypes to override default return values of ALogTypes
    LogTypes logTypes;

    public LogModel() {
        this.setLogType(LogType.DEFAULT);
    }
}
