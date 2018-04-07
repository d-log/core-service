package com.loggerproject.coreservice.data.document.log;

import com.loggerproject.coreservice.data.document.log.model.LogType;
import com.loggerproject.coreservice.data.document.log.model.ViewData;
import com.loggerproject.microserviceglobalresource.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(collection = "log")
public class LogModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    LogType logType;
    Set<String> directoryIDs;
    Set<String> tagIDs;
    String viewTemplateThemeID;
    List<ViewData> viewDatas;
}
