package com.loggerproject.coreservice.server.data.document.tag;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "tag")
public class TagModel extends GlobalModel {

    @Id
    String ID;
    String name;
    Set<String> logIDs;
    String imageID;
}
