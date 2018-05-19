package com.loggerproject.coreservice.service.tag.get;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class TagGetterRequest {
    String metadataNameLike;
    Long createdBefore; // unix milliseconds
    Pageable pageable;
}
