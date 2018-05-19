package com.loggerproject.coreservice.service.log.get.type;

import com.loggerproject.coreservice.data.model._shared.Metadata;
import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.ALogDisplayTypeOverride;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ATypeLogModelGetService {

    public abstract LogDisplayType getLogType();

    public List<ALogDisplayType> getAsLogType(Collection<LogModel> logModels) {
        List<ALogDisplayType> newLogModels = new ArrayList<>();
        for (LogModel lf : logModels) {
            ALogDisplayType aLogDisplayType = getAsLogType(lf);
            newLogModels.add(aLogDisplayType);
        }
        return newLogModels;
    }

    /**
     * Allows FileModel.data to be of type LinkedHashMap or LogFileData
     *
     * @param logModel
     * @return
     */
    public ALogDisplayType getAsLogType(LogModel logModel) {
        if (logModel == null) {
            return null;
        } else {
            return getByLogModelInternal(logModel);
        }
    }

    public abstract ALogDisplayType getByLogModelInternal(LogModel lf);

    protected String getID(ALogDisplayTypeOverride override, LogModel fl) {
        return fl.getId();
    }

    protected Metadata getMetadata(ALogDisplayTypeOverride override, LogModel fl) {
        return fl.getMetadata();
    }
}
