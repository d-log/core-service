package com.loggerproject.coreservice.server.data.document.directory;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@Document(collection = "directory")
public class DirectoryModel extends GlobalModel implements Serializable {

    @Id
    String ID;
    Set<String> parentIDs;
    Set<String> childrenIDs;
    Set<String> logIDs;
    String name;
    String description;
}