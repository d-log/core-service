package com.loggerproject.coreservice.service.data.view.manager.viewtemplate;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ViewTemplateModelUtilService {

    public String scrubAndValidateHTML(String html) throws Exception {
        if (StringUtils.isEmpty(html)) {
            throw new Exception("ViewTemplateModel.html cannot be blank");
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
