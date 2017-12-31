package com.loggerproject.directoryservice.server.controller.api.model.delete;

import com.loggerproject.microserviceglobalresource.pojo.controller.request.sub.RequestDeleteModel;
import lombok.Data;

import java.util.List;

@Data
public class RequestDeleteDirectoryModels extends RequestDeleteModel {
    List<String> ids;
}
