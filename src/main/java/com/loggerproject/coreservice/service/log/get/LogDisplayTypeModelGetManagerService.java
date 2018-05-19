package com.loggerproject.coreservice.service.log.get;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.service.log.get.regular.extra.LogGetterRequest;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.get.type.ATypeLogModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LogDisplayTypeModelGetManagerService {

    private HashMap<LogDisplayType, ATypeLogModelGetService> mapModel2GetService = new HashMap<>();

    @Autowired
    LogModelGetService logGetService;

    @Autowired
    public LogDisplayTypeModelGetManagerService(List<ATypeLogModelGetService> logTypeGetServices) throws Exception {
        for (ATypeLogModelGetService logTypeGetService : logTypeGetServices) {
            LogDisplayType logType = logTypeGetService.getLogType();
            if (logType == null) {
                throw new Exception(logTypeGetService.getClass().getSimpleName() + ".getLogDisplayType() cannot return null");
            }
            if (mapModel2GetService.put(logType, logTypeGetService) != null) {
                throw new Exception("Can't have multiple ATypedLogFileDataGetService serving the same LogType." + logType.toString());
            }
        }
    }

    public ALogDisplayType getRoot(LogDisplayType logType) throws Exception {
        if (logType == null || logType.equals(LogDisplayType.DEFAULT)) {
            return logGetService.getRoot();
        } else {
            LogModel log = logGetService.getRoot();
            return getServiceByLogType(logType).getAsLogType(log);
        }
    }

    public ALogDisplayType validateAndFindOne(String id, LogDisplayType logType) throws Exception {
        if (logType == null || logType.equals(LogDisplayType.DEFAULT)) {
            return logGetService.validateAndFindOne(id);
        } else {
            LogModel log = logGetService.validateAndFindOne(id);
            return getServiceByLogType(logType).getAsLogType(log);
        }
    }

    public ALogDisplayType findOne(String id, LogDisplayType logType) {
        if (logType == null || logType.equals(LogDisplayType.DEFAULT)) {
            return logGetService.findOne(id);
        } else {
            LogModel log = logGetService.findOne(id);
            return getServiceByLogType(logType).getAsLogType(log);
        }
    }

    public List getByIDs(Collection<String> ids, LogDisplayType logType) throws Exception {
        if (logType == null || logType.equals(LogDisplayType.DEFAULT)) {
            return logGetService.findByIds(ids);
        } else {
            List<LogModel> logs = logGetService.validateAndFindByIDs(ids);
            return getServiceByLogType(logType).getAsLogType(logs);
        }
    }

    public Page findAll(Pageable pageable, LogDisplayType logType) throws Exception {
        if (logType == null || logType.equals(LogDisplayType.DEFAULT)) {
            return logGetService.findAll(pageable);
        } else {
            Page<LogModel> page = logGetService.findAll(pageable);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getAsLogType(page.getContent()),
                    pageable,
                    page::getTotalElements);
        }
    }

    public Page theGetter(LogGetterRequest getterRequest, LogDisplayType logType) throws Exception {
        if (logType == null || logType.equals(LogDisplayType.DEFAULT)) {
            return logGetService.theGetter(getterRequest);
        } else {
            Page<LogModel> page = logGetService.theGetter(getterRequest);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getAsLogType(page.getContent()),
                    getterRequest.getPageable(),
                    page::getTotalElements);
        }
    }

    public ALogDisplayType getAsLogType(LogModel log, LogDisplayType logType) {
        return getServiceByLogType(logType).getAsLogType(log);
    }

    public List<ALogDisplayType> getAsLogType(Collection<LogModel> logs, LogDisplayType logType) {
        return getServiceByLogType(logType).getAsLogType(logs);
    }

    private ATypeLogModelGetService getServiceByLogType(LogDisplayType logType) {
        ATypeLogModelGetService getService = mapModel2GetService.get(logType);
        Assert.notNull(getService, "LogTypeGetManagerService does not contain key: '" + logType.toString() + "' available keys are: " + mapModel2GetService.keySet().toString());
        return getService;
    }
}
