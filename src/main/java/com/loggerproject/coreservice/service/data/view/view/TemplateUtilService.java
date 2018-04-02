package com.loggerproject.coreservice.service.data.view.view;

import com.loggerproject.coreservice.data.document.view.Template;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
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

    public Template validateAndFindOne(String viewID, String templateName) throws Exception {
        Template t = findOne(viewID, templateName);
        if (t == null) {
            throwModelNotFoundException(viewID, templateName, t);
        }
        return t;
    }

    public void validateId(String viewID, String templateName) throws Exception {
        Template t = findOne(viewID, templateName);
        if (t == null) {
            throwModelNotFoundException(viewID, templateName, t);
        }
    }

    public void validateIds(String viewID, Collection<String> templateNames) throws Exception {
        for (String templateName : templateNames) {
            validateId(viewID, templateName);
        }
    }

    public List<Template> findByIds(String viewID, Collection<String> templateNames) throws Exception {
        List<Template> models = new ArrayList<>();
        for (String templateName : templateNames) {
            Template t = findOne(viewID, templateName);
            models.add(t);
        }
        return models;
    }

    public Template findOne(String viewID, String templateName) throws Exception {
        ViewModel view = viewModelGetService.validateAndFindOne(viewID);
        return view.getTemplates().get(templateName);
    }

    protected void throwModelNotFoundException(String viewID, String templateName, Template t) throws Exception {
        throw new ModelNotFoundException(templateName, "ViewModel: '" + viewID + "' does not contain template: '" + templateName + "'");
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
