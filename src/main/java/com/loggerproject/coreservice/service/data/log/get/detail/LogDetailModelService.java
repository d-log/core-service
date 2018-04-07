package com.loggerproject.coreservice.service.data.log.get.detail;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.document.log.LogModel;
import com.loggerproject.coreservice.data.document.log.model.ViewData;
import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.service.data.log.get.detail.model.LogDetailModel;
import com.loggerproject.coreservice.service.data.log.get.detail.model.ViewDataDetailModel;
import com.loggerproject.coreservice.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LogDetailModelService {

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    ViewTemplateThemeModelGetService viewTemplateThemeModelGetService;

    public LogDetailModel findOne(String logID) throws Exception {
        LogModel logModel = logModelGetService.validateAndFindOne(logID);
        return this.findOne(logModel);
    }

    public LogDetailModel findOne(LogModel logModel) {
        LogDetailModel logDetailModel = new LogDetailModel();

        this.setLogDetailLogID(logDetailModel, logModel);
        this.setLogDetailMetaData(logDetailModel, logModel);
        this.setLogDetailDirectoryModels(logDetailModel, logModel);
        this.setLogDetailTagModels(logDetailModel, logModel);
        this.setLogDetailViewTemplateThemeModel(logDetailModel, logModel);
        this.setLogDetailViewModel(logDetailModel, logModel);
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
        List<DirectoryModel> directoryModels = directoryModelGetService.findByIds(logModel.getDirectoryIDs());
        logDetailModel.setDirectoryModels(directoryModels);
    }

    private void setLogDetailTagModels(LogDetailModel logDetailModel, LogModel logModel) {
        List<TagModel> tagModels = tagModelGetService.findByIds(logModel.getTagIDs());
        logDetailModel.setTagModels(tagModels);
    }

    private void setLogDetailViewTemplateThemeModel(LogDetailModel logDetailModel, LogModel logModel) {
        String id = logModel.getViewTemplateThemeID();
        if (id != null) {
            ViewTemplateThemeModel viewTemplateThemeModel = viewTemplateThemeModelGetService.findOne(id);
            logDetailModel.setViewTemplateThemeModel(viewTemplateThemeModel);
        }
    }

    private void setLogDetailViewModel(LogDetailModel logDetailModel, LogModel logModel) {
        Set<String> uniqueViewIDs = new HashSet<>();
        for (ViewData viewData : logModel.getViewDatas()) {
            uniqueViewIDs.add(viewData.getViewID());
        }
        List<ViewModel> viewModels = viewModelGetService.findByIds(uniqueViewIDs);
        logDetailModel.setViewModels(viewModels);
    }

    private void setLogDetailViewDataDetailModel(LogDetailModel logDetailModel, LogModel logModel) {
        List<ViewDataDetailModel> viewDataDetailModels = new ArrayList<>();

        for (ViewData vd : logModel.getViewDatas()) {
            ViewDataDetailModel viewDataDetailModel = new ViewDataDetailModel();

            viewDataDetailModel.setViewModelID(vd.getViewID());
            viewDataDetailModel.setAssignedViewTemplateModelID(vd.getViewTemplateID());
            viewDataDetailModel.setData(vd.getData());

            viewDataDetailModels.add(viewDataDetailModel);
        }

        logDetailModel.setViewDataDetailModels(viewDataDetailModels);
    }
}
