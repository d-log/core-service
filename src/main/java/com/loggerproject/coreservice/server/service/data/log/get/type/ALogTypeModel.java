package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;

import java.util.Set;

/**
 * Once a class extends this and is used in production DO NOT change class name as
 * it is used to map the class name to its corresponding ALogTypeGetService
 */
@Data
public abstract class ALogTypeModel extends GlobalModel {
    String ID;

    String title;
    String description;

    Set<String> directoryIDs;
    Set<String> tagIDs;
}
