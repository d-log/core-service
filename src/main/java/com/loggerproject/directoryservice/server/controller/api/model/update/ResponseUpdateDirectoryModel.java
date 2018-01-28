package com.loggerproject.directoryservice.server.controller.api.model.update;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.pojo.controller.response.sub.ResponseUpdateModel;
import lombok.Data;

@Data
public class ResponseUpdateDirectoryModel extends ResponseUpdateModel {
    DirectoryModel model;
}
