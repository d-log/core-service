package com.loggerproject.coreservice.service.log.get.detail.model;

import com.loggerproject.coreservice.data.model.directory.DirectoryModel;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.model.view.ViewModel;
import com.loggerproject.coreservice.data.model.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.model.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.microserviceglobalresource.server.data.MetaData;
import lombok.Data;

import java.util.List;

@Data
public class LogDetailModel {
    String logID;
    MetaData metadata;
    List<DirectoryModel> directoryModels;
    List<TagModel> tagModels;
    ViewTemplateThemeModel viewTemplateThemeModel;
    List<ViewDataDetailModel> viewDataDetailModels;
    List<ViewModel> viewModels;
    List<ViewTemplateModel> viewTemplateModels;
}
