package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

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
