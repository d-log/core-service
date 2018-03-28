package com.loggerproject.coreservice.data.viewtemplate.model;

import lombok.Data;

import java.util.List;

@Data
public class ViewTemplateJS {
    String code;
    List<String> modelIDs;
}
