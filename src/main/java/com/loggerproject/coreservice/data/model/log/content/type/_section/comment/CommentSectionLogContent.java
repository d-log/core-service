package com.loggerproject.coreservice.data.model.log.content.type._section.comment;


import lombok.Data;

@Data
public class CommentSectionLogContent {
    // this is needed for bypassing Empty Bean Serialization
    // see ObjectMapperConfiguration.java
    Boolean dummy;
}
