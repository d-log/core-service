package com.loggerproject.coreservice.server.data.document.customlogdata;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.customlogdata.extra.ValidateDataStatement;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "custom-log-data")
public class CustomLogDataModel extends GlobalModel {

    @Id
    String ID;
    String logDataType;
    @JsonRawValue
    String dataSchemaJSON;
    ValidateDataStatement validateDataStatement; // additional validations of logdata

    // this allows to serialize actual json which is within this CustomLogDataModel json
    // without it the CustomLogDataModel.dataSchemaJSON must be in string format with escaped " quotations
    @JsonSetter("dataSchemaJSON")
    void setDataSchemaJSONString(JsonNode data) {
        this.dataSchemaJSON = data.toString();
    }
}
