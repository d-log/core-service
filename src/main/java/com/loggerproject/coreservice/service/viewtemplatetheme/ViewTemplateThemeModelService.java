package com.loggerproject.coreservice.service.viewtemplatetheme;

import com.loggerproject.coreservice.data.model.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateThemeModelService extends GlobalServerService<ViewTemplateThemeModel> {

    @Autowired
    public ViewTemplateThemeModelService(ViewTemplateThemeModelRepositoryRestResource repository) {
        super(repository);
    }

    public void scrubAndValidate(ViewTemplateThemeModel model) throws Exception {
        // TODO do it
    }
}



