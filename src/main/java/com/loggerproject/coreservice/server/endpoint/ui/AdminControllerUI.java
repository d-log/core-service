package com.loggerproject.coreservice.server.endpoint.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminControllerUI {

    @GetMapping("/")
    public @ResponseBody
    String test() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <form method=\"POST\" action=\"/api/image-upload/file\" enctype=\"multipart/form-data\">\n" +
                "    <input type=\"file\" name=\"file\" /><br/><br/>\n" +
                "    <input type=\"submit\" value=\"Submit\" />\n" +
                "  </form>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
    }
}
