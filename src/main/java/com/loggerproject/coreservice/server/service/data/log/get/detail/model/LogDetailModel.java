package com.loggerproject.coreservice.server.service.data.log.get.detail.model;

import com.loggerproject.coreservice.global.server.document.model.MetaData;
import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class LogDetailModel extends ResourceSupport {
    String logID;
    MetaData metadata;
    List<DirectoryModel> directoryModels;
    List<TagModel> tagModels;
    List<LogData> logDatas;
}
