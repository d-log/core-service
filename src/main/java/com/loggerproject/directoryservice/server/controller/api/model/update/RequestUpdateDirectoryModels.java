package com.loggerproject.directoryservice.server.controller.api.model.update;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.pojo.controller.request.sub.RequestUpdateModel;
import lombok.Data;

import java.util.List;

@Data
public class RequestUpdateDirectoryModels extends RequestUpdateModel {
    List<DirectoryModel> models;
}
