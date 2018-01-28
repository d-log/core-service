package com.loggerproject.directoryservice.server.data.model;

import com.loggerproject.microserviceglobalresource.pojo.data.MetaDataModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "directory")
public class DirectoryModel {
    @Id
    String id;
    List<String> parentIDs;
    List<String> childrenIDs;
    List<String> logIDs;
    String name;
    String description;
    MetaDataModel metaData;
}
