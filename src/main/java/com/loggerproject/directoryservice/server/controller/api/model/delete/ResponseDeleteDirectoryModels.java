package com.loggerproject.directoryservice.server.controller.api.model.delete;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.pojo.controller.response.sub.ResponseDeleteModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDeleteDirectoryModels extends ResponseDeleteModel {
    List<DirectoryModel> deletedModels;
}
