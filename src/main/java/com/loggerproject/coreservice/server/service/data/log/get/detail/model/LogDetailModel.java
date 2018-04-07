package com.loggerproject.coreservice.server.service.data.log.get.detail.model;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.document.view.ViewModel;
import com.loggerproject.coreservice.global.server.document.model.MetaData;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class LogDetailModel extends ResourceSupport {
    String logID;
    MetaData metadata;
    List<DirectoryModel> directoryModels;
    List<TagModel> tagModels;
    List<ViewDataDetailModel> viewDataDetailModels;
    List<ViewModel> viewModels;
}
