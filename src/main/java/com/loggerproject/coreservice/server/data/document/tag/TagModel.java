package com.loggerproject.coreservice.server.data.document.tag;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "tag")
public class TagModel extends GlobalModel {

    @Id
    String ID;
    String name;
    Set<String> logIDs;
    String imageID;
}
