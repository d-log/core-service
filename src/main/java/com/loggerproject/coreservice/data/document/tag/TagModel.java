package com.loggerproject.coreservice.data.document.tag;

import com.loggerproject.microserviceglobalresource.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "tag")
public class TagModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    String name;
    Set<String> logIDs;
}