package com.loggerproject.coreservice.global.server.endpoint.api.model;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class GlobalResponse extends ResourceSupport {
    String message;
    Integer code;
}
