package com.loggerproject.directoryservice.server.controller.api.model.get;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.pojo.controller.response.sub.ResponseGetModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseGetDirectoryModels extends ResponseGetModel {
    List<DirectoryModel> models;
    List<String> nonExistentIDs;
}
