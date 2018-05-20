package com.loggerproject.coreservice.data.model.log.content.type._section.childlogs;


import lombok.Data;

@Data
public class ChildLogsSectionLogContent {
    // this is needed for bypassing Empty Bean Serialization
    // see ObjectMapperConfiguration.java
    Boolean dummy;
}
