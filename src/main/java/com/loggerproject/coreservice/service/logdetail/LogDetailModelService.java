package com.loggerproject.coreservice.service.logdetail;

import com.loggerproject.coreservice.data.directory.DirectoryModelService;
import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import com.loggerproject.coreservice.data.log.LogModelService;
import com.loggerproject.coreservice.data.log.model.LogModel;
import com.loggerproject.coreservice.data.log.model.SchemaDataSource;
import com.loggerproject.coreservice.data.log.model.ViewData;
import com.loggerproject.coreservice.data.tag.TagModelService;
import com.loggerproject.coreservice.data.tag.model.TagModel;
import com.loggerproject.coreservice.data.view.ViewModelService;
import com.loggerproject.coreservice.data.view.model.ViewModel;
import com.loggerproject.coreservice.data.viewtemplate.ViewTemplateModelService;
import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateModel;
import com.loggerproject.coreservice.data.viewtemplatetheme.ViewTemplateThemeModelService;
import com.loggerproject.coreservice.data.viewtemplatetheme.model.ViewTemplateThemeModel;
import com.loggerproject.coreservice.service.logdetail.model.LogDetailModel;
import com.loggerproject.coreservice.service.logdetail.model.ViewDataDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogDetailModelService {
    @Autowired
    LogModelService logModelService;

    @Autowired
    TagModelService tagModelService;

    @Autowired
    DirectoryModelService directoryModelService;

    @Autowired
    ViewModelService viewModelService;

    @Autowired
    ViewTemplateModelService viewTemplateModelService;

    @Autowired
    ViewTemplateThemeModelService viewTemplateThemeModelService;

    public LogDetailModel findOne(String logID) throws Exception {
        LogModel logModel = logModelService.validateAndFindOne(logID);
        return this.findOne(logModel);
    }

    public LogDetailModel findOne(LogModel logModel) {
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
        List<DirectoryModel> directoryModels = directoryModelService.findByIds(logModel.getDirectoryIDs());
        logDetailModel.setDirectoryModels(directoryModels);
    }

    private void setLogDetailTagModels(LogDetailModel logDetailModel, LogModel logModel) {
        List<TagModel> tagModels = tagModelService.findByIds(logModel.getTagIDs());
        logDetailModel.setTagModels(tagModels);
    }

    private void setLogDetailViewTemplateThemeModel(LogDetailModel logDetailModel, LogModel logModel) {
        ViewTemplateThemeModel viewTemplateThemeModel = viewTemplateThemeModelService.findOne(logModel.getViewTemplateThemeID());
        logDetailModel.setViewTemplateThemeModel(viewTemplateThemeModel);
    }

    private void setLogDetailViewModelAndViewTemplateModel(LogDetailModel logDetailModel, LogModel logModel) {
        Set<String> uniqueViewIDs = new HashSet<>();
        Set<String> uniqueViewTemplateIDs = new HashSet<>();
        for (ViewData viewData : logModel.getViewDatas()) {
            uniqueViewIDs.add(viewData.getSchemaDataSource().getViewID());
            uniqueViewTemplateIDs.add(viewData.getSchemaDataSource().getViewTemplateID());
        }

        List<ViewModel> viewModels = viewModelService.findByIds(uniqueViewIDs);

        uniqueViewTemplateIDs.addAll(
                viewModels.stream()
                        .map(ViewModel::getDefaultViewTemplateID)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );

        List<ViewTemplateModel> viewTemplateModels = viewTemplateModelService.findByIds(uniqueViewTemplateIDs);

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
