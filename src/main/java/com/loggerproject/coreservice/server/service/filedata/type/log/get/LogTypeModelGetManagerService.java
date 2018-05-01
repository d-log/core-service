package com.loggerproject.coreservice.server.service.filedata.type.log.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.LogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.extra.GetterRequest;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ATypedLogFileDataGetService;
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
public class LogTypeModelGetManagerService {

    private HashMap<LogType, ATypedLogFileDataGetService> mapModel2GetService = new HashMap<>();

    @Autowired
    LogFileDataGetService logGetService;

    @Autowired
    public LogTypeModelGetManagerService(List<ATypedLogFileDataGetService> logTypeGetServices) throws Exception {
        for (ATypedLogFileDataGetService logTypeGetService : logTypeGetServices) {
            LogType logType = logTypeGetService.getLogType();
            if (logType == null) {
                throw new Exception(logTypeGetService.getClass().getSimpleName() + ".getLogType() cannot return null");
            }
            if (mapModel2GetService.put(logType, logTypeGetService) instanceof ATypedLogFileDataGetService) {
                throw new Exception("Can't have multiple ATypedLogFileDataGetService serving the same LogType." + logType.toString());
            }
        }
    }

    public FileModel validateAndFindOne(String id, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.validateAndFindOne(id);
        } else {
            FileModel log = logGetService.validateAndFindOne(id);
            return getServiceByLogType(logType).getAsLogType(log);
        }
    }

    public FileModel findOne(String id, LogType logType) {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.findOne(id);
        } else {
            FileModel log = logGetService.findOne(id);
            return getServiceByLogType(logType).getAsLogType(log);
        }
    }

    public List getByIDs(Collection<String> ids, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.findByIds(ids);
        } else {
            List<FileModel> logs = logGetService.validateAndFindByIDs(ids);
            return getServiceByLogType(logType).getAsLogType(logs);
        }
    }

    public Page findAll(Pageable pageable, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.findAll(pageable);
        } else {
            Page<FileModel> page = logGetService.findAll(pageable);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getAsLogType(page.getContent()),
                    pageable,
                    page::getTotalElements);
        }
    }

    public Page theGetter(GetterRequest getterRequest, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.theGetter(getterRequest);
        } else {
            Page<FileModel> page = logGetService.theGetter(getterRequest);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getAsLogType(page.getContent()),
                    getterRequest.getPageable(),
                    page::getTotalElements);
        }
    }

    public FileModel getAsLogType(FileModel log, LogType logType) {
        return getServiceByLogType(logType).getAsLogType(log);
    }

    public List<FileModel> getAsLogType(Collection<FileModel> logs, LogType logType) {
        return getServiceByLogType(logType).getAsLogType(logs);
    }

    private ATypedLogFileDataGetService getServiceByLogType(LogType logType) {
        ATypedLogFileDataGetService getService = mapModel2GetService.get(logType);
        Assert.notNull(getService, "LogTypeGetManagerService does not contain key: '" + logType.toString() + "' available keys are: " + mapModel2GetService.keySet().toString());
        return getService;
    }
}
