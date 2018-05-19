package com.loggerproject.coreservice.data.model.tag;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "tag")
public class TagModel extends GlobalModel {
    @Id
    String id;

    Set<String> logIDs;
    String imageID;
}
