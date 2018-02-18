package com.loggerproject.coreservice.server.service.logdetail;

import com.loggerproject.coreservice.server.model.log.detail.LogDetailModel;
import com.loggerproject.coreservice.server.model.log.detail.ViewDataDetailModel;
import com.loggerproject.directoryservice.client.service.DirectoryClientService;
import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.imageservice.client.service.ImageClientService;
import com.loggerproject.logservice.server.data.model.LogModel;
import com.loggerproject.logservice.server.data.model.SchemaDataSource;
import com.loggerproject.logservice.server.data.model.ViewData;
import com.loggerproject.tagservice.client.service.TagClientService;
import com.loggerproject.tagservice.server.data.model.TagModel;
import com.loggerproject.viewservice.client.service.ViewClientService;
import com.loggerproject.viewservice.client.service.ViewTemplateThemeClientService;
import com.loggerproject.viewservice.server.data.model.ViewModel;
import com.loggerproject.viewservice.server.data.model.ViewTemplateThemeModel;
import com.loggerproject.viewtemplateservice.client.service.ViewTemplateClientService;
import com.loggerproject.viewtemplateservice.server.data.model.ViewTemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LogDetailModelFreshService {

    @Autowired
    ImageClientService imageClientService;

    @Autowired
    TagClientService tagClientService;

    @Autowired
    DirectoryClientService directoryClientService;

    @Autowired
    ViewTemplateThemeClientService viewTemplateThemeClientService;

    @Autowired
    ViewClientService viewClientService;

    @Autowired
    ViewTemplateClientService viewTemplateClientService;

    public LogDetailModel getLogDetailModel(LogModel logModel) {
        LogDetailModel logDetailModel = new LogDetailModel();

        this.setLogDetailLogID(logDetailModel, logModel);
        this.setLogDetailMetaData(logDetailModel, logModel);
        this.setLogDetailDirectoryModels(logDetailModel, logModel);
        this.setLogDetailTagModels(logDetailModel, logModel);
        this.setLogDetailViewTemplateThemeModel(logDetailModel, logModel);
        this.setLogDetailViewModelAndViewTemplateModel(logDetailModel, logModel);
        this.setLogDetailViewDataDetailModel(logDetailModel, logModel);

        return logDetailModel;
    }

    private void setLogDetailLogID(LogDetailModel logDetailModel, LogModel logModel) {
        logDetailModel.setLogID(logModel.getID());
    }

    private void setLogDetailMetaData(LogDetailModel logDetailModel, LogModel logModel) {
        logDetailModel.setMetadata(logModel.getMetadata());
    }

    private void setLogDetailDirectoryModels(LogDetailModel logDetailModel, LogModel logModel) {
        List<DirectoryModel> directoryModels = directoryClientService.findByIds(logModel.getDirectoryIDs());
        logDetailModel.setDirectoryModels(directoryModels);
    }

    private void setLogDetailTagModels(LogDetailModel logDetailModel, LogModel logModel) {
        List<TagModel> tagModels = tagClientService.findByIds(logModel.getTagIDs());
        logDetailModel.setTagModels(tagModels);
    }

    private void setLogDetailViewTemplateThemeModel(LogDetailModel logDetailModel, LogModel logModel) {
        ViewTemplateThemeModel viewTemplateThemeModel = viewTemplateThemeClientService.findOne(logModel.getViewTemplateThemeID());
        logDetailModel.setViewTemplateThemeModel(viewTemplateThemeModel);
    }

    private void setLogDetailViewModelAndViewTemplateModel(LogDetailModel logDetailModel, LogModel logModel) {
        Set<String> uniqueViewIDs = new HashSet<>();
        Set<String> uniqueViewTemplateIDs = new HashSet<>();
        for (ViewData viewData : logModel.getViewDatas()) {
            uniqueViewIDs.add(viewData.getSchemaDataSource().getViewID());
            uniqueViewTemplateIDs.add(viewData.getSchemaDataSource().getViewTemplateID());
        }

        List<ViewModel> viewModels = viewClientService.findByIds(uniqueViewIDs);

        uniqueViewTemplateIDs.addAll(
                viewModels.stream()
                        .map(ViewModel::getDefaultViewTemplateID)
                        .filter(viewTemplateID -> viewTemplateID != null)
                        .collect(Collectors.toList())
        );

        List<ViewTemplateModel> viewTemplateModels = viewTemplateClientService.findByIds(uniqueViewTemplateIDs);

        logDetailModel.setViewModels(viewModels);
        logDetailModel.setViewTemplateModels(viewTemplateModels);
    }

    private void setLogDetailViewDataDetailModel(LogDetailModel logDetailModel, LogModel logModel) {
        List<ViewDataDetailModel> viewDataDetailModels = new ArrayList<>();

        for (ViewData vd : logModel.getViewDatas()) {
            ViewDataDetailModel viewDataDetailModel = new ViewDataDetailModel();

            SchemaDataSource sds = vd.getSchemaDataSource();
            viewDataDetailModel.setViewModelID(sds.getViewID());
            viewDataDetailModel.setAssignedViewTemplateModelID(sds.getViewTemplateID());
            viewDataDetailModel.setData(vd.getData());

            viewDataDetailModels.add(viewDataDetailModel);
        }

        logDetailModel.setViewDataDetailModels(viewDataDetailModels);
    }
}
