package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type._noncontent.header;

import lombok.Data;

@Data
public class HeaderSectionLogData {
    // this is needed for bypassing Empty Bean Serialization
    // see ObjectMapperConfiguration.java
    Boolean dummy;
}
