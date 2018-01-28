package com.loggerproject.directoryservice.server.controller.api.model.create;

import com.loggerproject.microserviceglobalresource.pojo.controller.response.sub.ResponseCreateModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseCreateDirectoryModels extends ResponseCreateModel {
    List<ResponseCreateDirectoryModel> responses;
}
