package com.loggerproject.coreservice.data.model.log.content.type._section.descendant;


import lombok.Data;

@Data
public class DescendantLogsSectionLogContent {
    // this is needed for bypassing Empty Bean Serialization
    // see ObjectMapperConfiguration.java
    Boolean dummy;
}
