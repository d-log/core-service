package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.LogModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ALogTypeGetService<T extends ALogTypeModel> {

    public List<T> getByLogModels(Collection<LogModel> logs) {
        List<T> logTiles = new ArrayList<>();
        for (LogModel log : logs) {
            T logTile = getByLogModel(log);
            logTiles.add(logTile);
        }
        return logTiles;
    }

    public T getByLogModel(LogModel logModel) {
        if (logModel == null) {
            return null;
        } else {
            return getByLogModelInternal(logModel);
        }
    }

    public abstract T getByLogModelInternal(LogModel logModel);
}
