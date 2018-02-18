package com.loggerproject.coreservice.server.model.log.detail;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.server.data.MetaData;
import com.loggerproject.tagservice.server.data.model.TagModel;
import com.loggerproject.viewservice.server.data.model.ViewModel;
import com.loggerproject.viewservice.server.data.model.ViewTemplateThemeModel;
import com.loggerproject.viewtemplateservice.server.data.model.ViewTemplateModel;
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
