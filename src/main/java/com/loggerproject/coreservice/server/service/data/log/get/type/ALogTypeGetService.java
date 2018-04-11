package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ALogTypeGetService<T> {

    @Autowired
    LogModelGetService logModelGetService;

    public T getByID(String id) throws Exception {
        LogModel log = logModelGetService.validateAndFindOne(id);
        return getByLogModel(log);
    }

    public List<T> getByIDs(Collection<String> ids) throws Exception {
        List<LogModel> logs = logModelGetService.validateAndFindByIDs(ids);
        return getByLogModels(logs);
    }

    public List<T> getByLogModels(Collection<LogModel> logs) {
        List<T> logTiles = new ArrayList<>();
        for (LogModel log : logs) {
            T logTile = getByLogModel(log);
            logTiles.add(logTile);
        }
        return logTiles;
    }

    public abstract T getByLogModel(LogModel log);
}
