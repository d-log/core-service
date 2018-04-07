package com.loggerproject.coreservice.server.data.document.view;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.loggerproject.coreservice.server.data.document.view.model.ValidateDataStatement;
import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
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
    String name;
    String dataSchemaJSON;
    ValidateDataStatement validateDataStatement; // additional validations of logdata
    String defaultTemplateName;
    Map<String, Template> templates; // String template id/name -> Template

    // this allows to serialize actual json which is within this ViewModel json
    // without it the ViewModel.dataSchemaJSON must be in string format with escaped " quotations
    @JsonSetter("dataSchemaJSON")
    void setDataSchemaJSONString(JsonNode data)
    {
        this.dataSchemaJSON = data.toString();
    }
}
