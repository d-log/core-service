package com.loggerproject.coreservice.endpoint.api.log.model;

import com.loggerproject.coreservice.data.document.log.model.ViewData;
import lombok.Data;

import java.util.List;

@Data
public class UpdateViewDatasRequest {
    String id;
    List<ViewData> viewDatas;
}
