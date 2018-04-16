package com.loggerproject.coreservice.server.service.data.log.get;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * wrapper class for LogModel and *LogType get services
 */
@Service
@SuppressWarnings(value = "unchecked")
public class LogTypeModelGetManagerService {

    private HashMap<LogType, ALogTypeGetService> mapModel2GetService = new HashMap<>();

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public LogTypeModelGetManagerService(List<ALogTypeGetService> logTypeGetServices) throws Exception {
        for (ALogTypeGetService logTypeGetService : logTypeGetServices) {
            ALogTypeModel logTypeModel = (ALogTypeModel)GenericTypeResolver.resolveTypeArgument(logTypeGetService.getClass(), ALogTypeGetService.class).newInstance();
            mapModel2GetService.put(logTypeModel.getLogType(), logTypeGetService);
        }
    }

    public GlobalModel validateAndFindOne(String id, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logModelGetService.validateAndFindOne(id);
        } else {
            LogModel log = logModelGetService.validateAndFindOne(id);
            return getServiceByLogType(logType).getByLogModel(log);
        }
    }

    public GlobalModel findOne(String id, LogType logType) {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logModelGetService.findOne(id);
        } else {
            LogModel log = logModelGetService.findOne(id);
            return getServiceByLogType(logType).getByLogModel(log);
        }
    }

    public List getByIDs(Collection<String> ids, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logModelGetService.findByIds(ids);
        } else {
            List<LogModel> logs = logModelGetService.validateAndFindByIDs(ids);
            return getServiceByLogType(logType).getByLogModels(logs);
        }
    }

    public Page findAll(Pageable pageable, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logModelGetService.findAll(pageable);
        } else {
            Page<LogModel> page = logModelGetService.findAll(pageable);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getByLogModels(page.getContent()),
                    pageable,
                    page::getTotalElements);
        }
    }

    public Page theGetter(GetterRequest getterRequest, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logModelGetService.theGetter(getterRequest);
        } else {
            Page<LogModel> page = logModelGetService.theGetter(getterRequest);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getByLogModels(page.getContent()),
                    getterRequest.getPageable(),
                    page::getTotalElements);
        }
    }

    public ALogTypeModel getByLogModel(LogModel log, LogType logType) {
        return getServiceByLogType(logType).getByLogModel(log);
    }

    public List<ALogTypeModel> getByLogModels(Collection<LogModel> logs, LogType logType) {
        return getServiceByLogType(logType).getByLogModels(logs);
    }

    private ALogTypeGetService getServiceByLogType(LogType logType) {
        ALogTypeGetService getService = mapModel2GetService.get(logType);
        Assert.notNull(getService, "LogTypeGetManagerService does not contain key: '" + logType.toString() + "' available keys are: " + mapModel2GetService.keySet().toString());
        return getService;
    }
}
