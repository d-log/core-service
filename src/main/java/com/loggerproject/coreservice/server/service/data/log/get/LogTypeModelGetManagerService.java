package com.loggerproject.coreservice.server.service.data.log.get;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@SuppressWarnings(value = "unchecked")
public class LogTypeModelGetManagerService {

    // LogType to get-service
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

    public GlobalModel findOne(String id, LogType logType) throws Exception {
        if (logType == null) {
            return logModelGetService.findOne(id);
        } else {
            return getServiceByLogType(logType).findOne(id);
        }
    }

    public List getByIDs(Collection<String> ids, LogType logType) throws Exception {
        if (logType == null) {
            return logModelGetService.findByIds(ids);
        } else {
            return getServiceByLogType(logType).findByIDs(ids);
        }
    }

    public Page findAll(Pageable pageable, LogType logType) throws Exception {
        if (logType == null) {
            return logModelGetService.findAll(pageable);
        } else {
            return getServiceByLogType(logType).findAll(pageable);
        }
    }

    public Page theGetter(Date dateThreshold, Pageable pageable, LogType logType) throws Exception {
        if (logType == null) {
            return logModelGetService.theGetter(dateThreshold, pageable);
        } else {
            return getServiceByLogType(logType).theGetter(dateThreshold, pageable);
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
