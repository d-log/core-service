package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LogTypeGetManagerService {

    // model simple-name to get-service
    private HashMap<String, ALogTypeGetService> mapModel2GetService = new HashMap<>();

    @Autowired
    public LogTypeGetManagerService(List<ALogTypeGetService> logTypeGetServices) {
        for (ALogTypeGetService logTypeGetService : logTypeGetServices) {
            String name = GenericTypeResolver.resolveTypeArgument(logTypeGetService.getClass(), ALogTypeGetService.class).getSimpleName();
            mapModel2GetService.put(name, logTypeGetService);
        }
    }

    public Object getByID(String id, String model) throws Exception {
        return getServiceByModel(model).getByID(id);
    }

    public Object getByIDs(List<String> id, String model) throws Exception {
        return getServiceByModel(model).getByIDs(id);
    }

    public Object getByLogModel(LogModel log, String model) throws Exception {
        return getServiceByModel(model).getByLogModel(log);
    }

    public Object getByLogModels(List<LogModel> logs, String model) throws Exception {
        return getServiceByModel(model).getByLogModels(logs);
    }

    private ALogTypeGetService getServiceByModel(String model) throws Exception {
        ALogTypeGetService getService = mapModel2GetService.get(model);
        if (getService != null) {
            return getService;
        } else {
            throw new Exception("LogTypeGetManagerService does not contain key: '" + model + "' available keys are: " + mapModel2GetService.keySet().toString());
        }
    }
}
