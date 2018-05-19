package com.loggerproject.coreservice.data.model.log;

import com.loggerproject.coreservice.data.model.log.override.LogDisplayOverride;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "log")
public class LogModel extends ALogDisplayType {
    @Id
    String id;

    LogDisplayOverride logDisplayOverride;

    public LogModel() {
        this.setLogDisplayType(LogDisplayType.DEFAULT);
    }
}
