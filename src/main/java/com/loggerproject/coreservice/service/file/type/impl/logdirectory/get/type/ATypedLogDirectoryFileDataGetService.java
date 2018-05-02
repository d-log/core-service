package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.ALogDirectoryFileDataOverride;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ATypedLogDirectoryFileDataGetService {

    @Autowired
    ObjectMapper objectMapper;

    public abstract LogDirectoryType getLogType();

    public List<FileModel> getAsLogDirectoryType(Collection<FileModel> lfs) {
        List<FileModel> nlfs = new ArrayList<>();
        for (FileModel lf : lfs) {
            FileModel logTile = getAsLogDirectoryType(lf);
            nlfs.add(logTile);
        }
        return nlfs;
    }

    /**
     * Allows FileModel.data to be of type LinkedHashMap or LogDirectoryFileData
     *
     * @param lf
     * @return
     */
    public FileModel getAsLogDirectoryType(FileModel lf) {
        if (lf == null) {
            return null;
        } else {
            if (!LogDirectoryFileData.class.isInstance(lf.getData())) {
                lf.setData(objectMapper.convertValue(lf.getData(), LogDirectoryFileData.class));
            }
            return getByLogDirectoryModelInternal(lf);
        }
    }

    public abstract FileModel getByLogDirectoryModelInternal(FileModel lf);

    protected String getID(ALogDirectoryFileDataOverride override, FileModel fl) {
        return fl.getId();
    }

    protected Metadata getMetadata(ALogDirectoryFileDataOverride override, FileModel fl) {
        return fl.getMetadata();
    }
}
