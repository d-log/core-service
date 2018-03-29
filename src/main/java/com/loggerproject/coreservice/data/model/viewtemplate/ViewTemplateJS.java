package com.loggerproject.coreservice.data.model.viewtemplate;

import lombok.Data;

import java.util.List;

@Data
public class ViewTemplateJS {
    String code;
    List<String> modelIDs;
}
