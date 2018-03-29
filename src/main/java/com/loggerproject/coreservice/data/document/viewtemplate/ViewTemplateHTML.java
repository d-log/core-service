package com.loggerproject.coreservice.data.document.viewtemplate;

import lombok.Data;

import java.util.List;

@Data
public class ViewTemplateHTML {
    String code;
    List<String> modelIDs;
}
