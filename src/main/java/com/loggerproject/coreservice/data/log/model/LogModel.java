package com.loggerproject.coreservice.data.log.model;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "log")
public class LogModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    List<String> directoryIDs;
    List<String> tagIDs;
    String viewTemplateThemeID;
    List<ViewData> viewDatas;
}
