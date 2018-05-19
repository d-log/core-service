package com.loggerproject.coreservice.data.model.log.content.type._section.header;

import lombok.Data;

@Data
public class HeaderSectionLogContent {
    // this is needed for bypassing Empty Bean Serialization
    // see ObjectMapperConfiguration.java
    Boolean dummy;
}
