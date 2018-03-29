package com.loggerproject.coreservice.service.viewtemplatetheme;

import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.microserviceglobalresource.server.service.GlobalModelServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateThemeModelService extends GlobalModelServerService<ViewTemplateThemeModel> {

    @Autowired
    public ViewTemplateThemeModelService(ViewTemplateThemeModelRepositoryRestResource repository) {
        super(repository);
    }

    public void scrubAndValidate(ViewTemplateThemeModel model) throws Exception {
        // TODO do it
    }
}



