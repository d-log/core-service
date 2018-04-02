package com.loggerproject.coreservice.data.document.view;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.loggerproject.microserviceglobalresource.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "view")
public class ViewModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    String dataSchemaJSON;
    String defaultTemplateName;
    Map<String, Template> templates; // String template name -> Template

    @JsonSetter("dataSchemaJSON")
    void setDataSchemaJSONString(JsonNode data)
    {
        this.dataSchemaJSON = data.toString();
    }
}
