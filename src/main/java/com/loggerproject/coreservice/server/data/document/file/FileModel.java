package com.loggerproject.coreservice.server.data.document.file;

import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "file")
public class FileModel {

    @Id
    String id;

    Metadata metadata;

    Object data;
}
