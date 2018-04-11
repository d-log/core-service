package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class LogTypeGetManagerService {

    // LogType to get-service
    private HashMap<LogType, ALogTypeGetService> mapModel2GetService = new HashMap<>();

    @Autowired
    public LogTypeGetManagerService(List<ALogTypeGetService> logTypeGetServices) throws Exception {
        for (ALogTypeGetService logTypeGetService : logTypeGetServices) {
            ALogTypeModel logTypeModel = (ALogTypeModel)GenericTypeResolver.resolveTypeArgument(logTypeGetService.getClass(), ALogTypeGetService.class).newInstance();
            mapModel2GetService.put(logTypeModel.getLogType(), logTypeGetService);
        }
    }

    public Object getByID(String id, LogType logType) throws Exception {
        return getServiceByModel(logType).getByID(id);
    }

    public List<Object> getByIDs(Collection<String> id, LogType logType) throws Exception {
        return getServiceByModel(logType).getByIDs(id);
    }

    public Object getByLogModel(LogModel log, LogType logType) throws Exception {
        return getServiceByModel(logType).getByLogModel(log);
    }

    public List<Object> getByLogModels(Collection<LogModel> logs, LogType logType) throws Exception {
        return getServiceByModel(logType).getByLogModels(logs);
    }

    private ALogTypeGetService getServiceByModel(LogType logType) throws Exception {
        ALogTypeGetService getService = mapModel2GetService.get(logType);
        if (getService != null) {
            return getService;
        } else {
            throw new Exception("LogTypeGetManagerService does not contain key: '" + logType.toString() + "' available keys are: " + mapModel2GetService.keySet().toString());
        }
    }
}
