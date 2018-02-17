package com.loggerproject.coreservice.server.controller.api.model;

import lombok.Data;

@Data
public class ViewDataDetailModel {
    String viewModelID;
    String assignedViewTemplateModelID;
    String data;
}
