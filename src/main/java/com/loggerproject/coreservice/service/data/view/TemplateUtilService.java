package com.loggerproject.coreservice.service.data.view;

import com.loggerproject.coreservice.data.document.view.Template;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.data.view.get.ViewModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.get.model.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TemplateUtilService {
    
    @Autowired
    ViewModelGetService viewModelGetService;

    public Template validateAndFindOne(String viewID, String templateID) throws Exception {
        Template t = findOne(viewID, templateID);
        if (t == null) {
            throwModelNotFoundException(viewID, templateID);
        }
        return t;
    }

    public void validateId(String viewID, String templateID) throws Exception {
        Template t = findOne(viewID, templateID);
        if (t == null) {
            throwModelNotFoundException(viewID, templateID);
        }
    }

    public void validateIds(String viewID, Collection<String> templateIDs) throws Exception {
        for (String templateID : templateIDs) {
            validateId(viewID, templateID);
        }
    }

    public List<Template> findByIds(String viewID, Collection<String> templateIDs) throws Exception {
        List<Template> models = new ArrayList<>();
        for (String templateID : templateIDs) {
            Template t = findOne(viewID, templateID);
            models.add(t);
        }
        return models;
    }

    public Template findOne(String viewID, String templateID) throws Exception {
        ViewModel view = viewModelGetService.validateAndFindOne(viewID);
        return view.getTemplates().get(templateID);
    }

    protected void throwModelNotFoundException(String viewID, String templateID) throws Exception {
        throw new ModelNotFoundException(templateID, "ViewModel: '" + viewID + "' does not contain template: '" + templateID + "'");
    }

    public Template scrubAndValidateTemplate(Template template) throws Exception {
        String html = scrubAndValidateHTML(template.getHtml());
        String js   = scrubAndValidateJS(template.getJs());
        String css  = scrubAndValidateCSS(template.getCss());

        template.setHtml(html);
        template.setJs(js);
        template.setCss(css);

        return template;
    }

    public String scrubAndValidateHTML(String html) throws Exception {
        if (StringUtils.isEmpty(html)) {
            throw new Exception("Template.html cannot be blank");
        }
        return html;
    }

    public String scrubAndValidateJS(String js) {
        if (js == null) {
            return "";
        }
        return js;
    }

    public String scrubAndValidateCSS(String css) {
        if (css == null) {
            return "";
        }
        return css;
    }
}
