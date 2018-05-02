package com.loggerproject.coreservice.server.service.filedata.type.log.get.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.ALogFileDataOverride;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ATypedLogFileDataGetService {

    @Autowired
    ObjectMapper objectMapper;

    public abstract LogType getLogType();

    public List<FileModel> getAsLogType(Collection<FileModel> lfs) {
        List<FileModel> nlfs = new ArrayList<>();
        for (FileModel lf : lfs) {
            FileModel logTile = getAsLogType(lf);
            nlfs.add(logTile);
        }
        return nlfs;
    }

    /**
     * Allows FileModel.data to be of type LinkedHashMap or LogFileData
     *
     * @param lf
     * @return
     */
    public FileModel getAsLogType(FileModel lf) {
        if (lf == null) {
            return null;
        } else {
            if (!LogFileData.class.isInstance(lf.getData())) {
                lf.setData(objectMapper.convertValue(lf.getData(), LogFileData.class));
            }
            return getByLogModelInternal(lf);
        }
    }

    public abstract FileModel getByLogModelInternal(FileModel lf);

    protected String getID(ALogFileDataOverride override, FileModel fl) {
        return fl.getId();
    }

    protected Metadata getMetadata(ALogFileDataOverride override, FileModel fl) {
        return fl.getMetadata();
    }
}
