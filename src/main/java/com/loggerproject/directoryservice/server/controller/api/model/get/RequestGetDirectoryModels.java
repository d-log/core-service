package com.loggerproject.directoryservice.server.controller.api.model.get;

import com.loggerproject.microserviceglobalresource.pojo.controller.request.sub.RequestGetModel;
import lombok.Data;

import java.util.List;

@Data
public class RequestGetDirectoryModels extends RequestGetModel {
    List<String> ids;
}
