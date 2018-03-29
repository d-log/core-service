package com.loggerproject.coreservice.data.document.directory;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "directory")
public class DirectoryModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    Set<String> parentIDs;
    Set<String> childrenIDs;
    Set<String> logIDs;
    String name;
    String description;
}
