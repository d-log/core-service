package com.loggerproject.coreservice.service.image.get;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class ImageGetterRequest {
    Long createdBefore; // unix milliseconds
    Pageable pageable;
}
