package com.loggerproject.directoryservice.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "directory")
public class DirectoryModel extends com.loggerproject.globalresource.service.directory.pojo.model.DirectoryModel {

    @Id
    @Override
    public void setId(String id) {
        super.setId(id);
    }
}
