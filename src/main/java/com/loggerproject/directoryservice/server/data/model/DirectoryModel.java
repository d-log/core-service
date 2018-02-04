package com.loggerproject.directoryservice.server.data.model;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "directory")
public class DirectoryModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    List<String> parentIDs;
    List<String> childrenIDs;
    List<String> logIDs;
    String name;
    String description;
}
