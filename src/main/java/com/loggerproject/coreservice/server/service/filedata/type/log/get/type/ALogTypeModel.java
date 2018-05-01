package com.loggerproject.coreservice.server.service.filedata.type.log.get.type;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.ALogModelTypeModel;
import lombok.Data;

import java.util.List;

/**
 * Mirrors FileModel with data as LogFileData object
 */
@Data
public abstract class ALogTypeModel extends ALogModelTypeModel {
    String ID;
    Metadata metadata;

    Organization organization;
    List<LogData> logDatas;
}
