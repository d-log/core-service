package com.loggerproject.coreservice.service.model;

import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import com.loggerproject.coreservice.data.tag.model.TagModel;
import com.loggerproject.coreservice.data.view.model.ViewModel;
import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateModel;
import com.loggerproject.coreservice.data.viewtemplatetheme.model.ViewTemplateThemeModel;
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
