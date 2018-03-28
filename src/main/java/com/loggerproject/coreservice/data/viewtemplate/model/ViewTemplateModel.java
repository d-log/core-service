package com.loggerproject.coreservice.data.viewtemplate.model;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "view-template")
public class ViewTemplateModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    String viewID;
    String name;
    ViewTemplateHTML html;
    ViewTemplateJS js;
    ViewTemplateCSS css;
}
