package com.loggerproject.coreservice.service.viewtemplate;

import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateCSS;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateJS;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.microserviceglobalresource.server.service.GlobalModelServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewTemplateModelService extends GlobalModelServerService<ViewTemplateModel> {

    @Autowired
    public ViewTemplateModelService(ViewTemplateModelRepositoryRestResource repository) {
        super(repository);
    }

    public void scrubAndValidate(ViewTemplateModel model) throws Exception {
        this.scrubAndValidateHTML(model);
        this.scrubAndValidateJS(model);
        this.scrubAndValidateCSS(model);
    }

    private void scrubAndValidateHTML(ViewTemplateModel model) throws Exception {
        Assert.notNull(model.getHtml(), "ViewTemplateModel.viewTemplateHtml cannot be null");
        Assert.hasLength(model.getHtml().getCode(), "ViewTemplateModel.viewTemplateHtml.code cannot be empty");
    }

    private void scrubAndValidateJS(ViewTemplateModel model) throws Exception {
        if (model.getJs() == null) {
            model.setJs(new ViewTemplateJS());
        }
    }

    private void scrubAndValidateCSS(ViewTemplateModel model) throws Exception {
        if (model.getCss() == null) {
            model.setCss(new ViewTemplateCSS());
        }
    }
}
