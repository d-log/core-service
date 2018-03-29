package com.loggerproject.coreservice.data.document.viewtemplatetheme;

import com.loggerproject.microserviceglobalresource.server.data.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "view-template-theme")
public class ViewTemplateThemeModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    List<ViewToViewTemplate> map;
}
