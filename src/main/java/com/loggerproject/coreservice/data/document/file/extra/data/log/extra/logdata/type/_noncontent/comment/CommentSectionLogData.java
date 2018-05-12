package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type._noncontent.comment;


import lombok.Data;

@Data
public class CommentSectionLogData {
    // this is needed for bypassing Empty Bean Serialization
    // see ObjectMapperConfiguration.java
    Boolean dummy;
}
