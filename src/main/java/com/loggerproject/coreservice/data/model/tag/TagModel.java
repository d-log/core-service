package com.loggerproject.coreservice.data.model.tag;

import com.loggerproject.coreservice.data.model._shared.IGlobalModel;
import com.loggerproject.coreservice.data.model._shared.Metadata;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "tag")
public class TagModel implements IGlobalModel {
    @Id
    String id;
    Metadata metadata;

    Set<String> logIDs;
    String imageID;
}
