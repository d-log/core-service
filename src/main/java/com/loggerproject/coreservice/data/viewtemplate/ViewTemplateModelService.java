package com.loggerproject.coreservice.data.viewtemplate;

import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateCSS;
import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateJS;
import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateModel;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewTemplateModelService extends GlobalServerService<ViewTemplateModel> {

    @Autowired
    public ViewTemplateModelService(ViewTemplateModelRepository repository) {
        super(repository);
    }

    protected ViewTemplateModel scrubAndValidate(ViewTemplateModel model) throws Exception {
        this.scrubAndValidateHTML(model);
        this.scrubAndValidateJS(model);
        this.scrubAndValidateCSS(model);
        return model;
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
