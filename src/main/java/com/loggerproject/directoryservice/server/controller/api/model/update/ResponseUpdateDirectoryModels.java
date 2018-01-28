package com.loggerproject.directoryservice.server.controller.api.model.update;

import com.loggerproject.microserviceglobalresource.pojo.controller.response.sub.ResponseUpdateModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseUpdateDirectoryModels extends ResponseUpdateModel {
    List<ResponseUpdateDirectoryModel> responses;
}
