package com.loggerproject.coreservice.data.document.view;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

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
    Set<String> otherViewTemplateIDs;
}
