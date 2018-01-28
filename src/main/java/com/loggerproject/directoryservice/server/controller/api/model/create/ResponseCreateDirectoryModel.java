package com.loggerproject.directoryservice.server.controller.api.model.create;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.pojo.controller.response.sub.ResponseCreateModel;
import lombok.Data;

@Data
public class ResponseCreateDirectoryModel extends ResponseCreateModel {
    DirectoryModel model;
}
