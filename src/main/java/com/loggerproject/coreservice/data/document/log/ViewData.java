package com.loggerproject.coreservice.data.document.log;

import lombok.Data;

@Data
public class ViewData {
    String viewID;
    String viewName;
    String viewTemplateID;
    String viewTemplateName;
    String data;
}
