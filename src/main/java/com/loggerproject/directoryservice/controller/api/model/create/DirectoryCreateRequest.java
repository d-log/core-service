package com.loggerproject.directoryservice.controller.api.model.create;

import lombok.Data;

import java.util.List;

@Data
public class DirectoryCreateRequest {

    List<String> paths;
    List<String> tags;
}
