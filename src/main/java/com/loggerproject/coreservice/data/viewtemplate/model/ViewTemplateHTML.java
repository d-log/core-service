package com.loggerproject.coreservice.data.viewtemplate.model;

import lombok.Data;

import java.util.List;

@Data
public class ViewTemplateHTML {
    String code;
    List<String> htmlModelIDs;
}
