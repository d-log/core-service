package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class ALogTypeGetService<T extends ALogTypeModel> {

    @Autowired
    LogModelGetService logModelGetService;

    public T findOne(String id) throws Exception {
        LogModel log = logModelGetService.validateAndFindOne(id);
        return getByLogModel(log);
    }

    public List<T> findByIDs(Collection<String> ids) throws Exception {
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

    public Page<T> findAll(Pageable pageable) throws Exception {
        Page<LogModel> page = logModelGetService.findAll(pageable);
        return PageableExecutionUtils.getPage(
                getByLogModels(page.getContent()),
                pageable,
                page::getTotalElements);
    }

    public Page<T> theGetter(Date dateThreshold, Pageable pageable) throws Exception {
        Page<LogModel> page = logModelGetService.theGetter(dateThreshold, pageable);
        return PageableExecutionUtils.getPage(
                getByLogModels(page.getContent()),
                pageable,
                page::getTotalElements);
    }

    public abstract T getByLogModel(LogModel log);
}
