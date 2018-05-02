package com.loggerproject.coreservice.endpoint.api.extra;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalResponse extends ResourceSupport {
    String message;
    Integer code;
}
