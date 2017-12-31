package com.loggerproject.directoryservice.server.controller.api.model.create;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.pojo.controller.request.sub.RequestCreateModel;
import lombok.Data;

import java.util.List;

@Data
public class RequestCreateDirectoryModels extends RequestCreateModel {
    List<DirectoryModel> models;
}
