package com.loggerproject.coreservice.service.data.view.manager.model;

import lombok.Data;

@Data
public class CreateViewTemplateViewRequest {
    String jsonSchema;
    String html;
    String js;
    String css;
}
