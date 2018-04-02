package com.loggerproject.coreservice.service.data.view.viewtemplatetheme.create;

import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewToViewTemplate;
import com.loggerproject.coreservice.data.repository.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.delete.ViewTemplateThemeModelDeleteService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.update.ViewTemplateThemeModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Service
public class ViewTemplateThemeModelCreateService extends GlobalServerCreateService<ViewTemplateThemeModel> {

    @Autowired
    ViewTemplateThemeModelRepositoryRestResource ViewTemplateThemeModelRepositoryRestResource;

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    public ViewTemplateThemeModelCreateService(ViewTemplateThemeModelRepositoryRestResource repository,
                                               @Lazy ViewTemplateThemeModelCreateService globalServerCreateService,
                                               @Lazy ViewTemplateThemeModelDeleteService globalServerDeleteService,
                                               @Lazy ViewTemplateThemeModelGetService globalServerGetService,
                                               @Lazy ViewTemplateThemeModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public ViewTemplateThemeModel scrubAndValidate(@NotNull ViewTemplateThemeModel model) throws Exception {
        if(CollectionUtils.isEmpty(model.getMap())) {
            throw new IllegalArgumentException("ViewTemplateThemeModel.map size cannot be empty");
        }

        Set<String> viewModelIDs = new HashSet<>();
        Set<String> viewTemplateIDs = new HashSet<>();

        for (ViewToViewTemplate t : model.getMap()) {
            Assert.notNull(t.getViewID(), "view id cannot be null in ViewTemplateThemeModel.map");
            Assert.notNull(t.getViewID(), "view template id cannot be null in ViewTemplateThemeModel.map");

            viewModelIDs.add(t.getViewID());
            viewTemplateIDs.add(t.getViewTemplateID());
        }

        viewModelGetService.validateIds(viewModelIDs);
//        viewTemplateModelGetService.validateIds(viewTemplateIDs);

        return model;
    }

    @Override
    protected ViewTemplateThemeModel beforeSave(ViewTemplateThemeModel model) throws Exception {
        model = scrubAndValidate(model);
        return super.beforeSave(model);
    }
}
