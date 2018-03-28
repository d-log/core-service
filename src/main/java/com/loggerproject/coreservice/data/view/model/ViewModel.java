package com.loggerproject.coreservice.data.view.model;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "view")
public class ViewModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    DataSchema dataSchema;
    String defaultViewTemplateID;
    List<String> otherViewTemplateIDs;
}
