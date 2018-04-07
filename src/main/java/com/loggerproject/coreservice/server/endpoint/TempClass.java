package com.loggerproject.coreservice.server.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempClass {

    @GetMapping("/home")
    public String test() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <form method=\"POST\" action=\"/api/image-upload/file\" enctype=\"multipart/form-logdata\">\n" +
                "    <input type=\"file\" name=\"file\" /><br/><br/>\n" +
                "    <input type=\"submit\" value=\"Submit\" />\n" +
                "  </form>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
    }
}
