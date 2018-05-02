package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.service.file.extra.FileGetterRequest;
import com.loggerproject.coreservice.service.file.type.impl.log.get.regular.LogFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.ATypedLogDirectoryFileDataGetService;
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
public class TypedLogDirectoryFileModelGetManagerService {

    private HashMap<LogDirectoryType, ATypedLogDirectoryFileDataGetService> mapModel2GetService = new HashMap<>();

    @Autowired
    LogFileModelGetService logGetService;

    @Autowired
    public TypedLogDirectoryFileModelGetManagerService(List<ATypedLogDirectoryFileDataGetService> logTypeGetServices) throws Exception {
        for (ATypedLogDirectoryFileDataGetService logTypeGetService : logTypeGetServices) {
            LogDirectoryType logDirectoryType = logTypeGetService.getLogType();
            if (logDirectoryType == null) {
                throw new Exception(logTypeGetService.getClass().getSimpleName() + ".getLogType() cannot return null");
            }
            if (mapModel2GetService.put(logDirectoryType, logTypeGetService) instanceof ATypedLogDirectoryFileDataGetService) {
                throw new Exception("Can't have multiple ATypedLogFileDataGetService serving the same LogType." + logDirectoryType.toString());
            }
        }
    }

    public FileModel validateAndFindOne(String id, LogDirectoryType logDirectoryType) throws Exception {
        if (logDirectoryType == null || logDirectoryType.equals(LogDirectoryType.DEFAULT)) {
            return logGetService.validateAndFindOne(id);
        } else {
            FileModel log = logGetService.validateAndFindOne(id);
            return getServiceByLogType(logDirectoryType).getAsLogDirectoryType(log);
        }
    }

    public FileModel findOne(String id, LogDirectoryType logDirectoryType) {
        if (logDirectoryType == null || logDirectoryType.equals(LogDirectoryType.DEFAULT)) {
            return logGetService.findOne(id);
        } else {
            FileModel log = logGetService.findOne(id);
            return getServiceByLogType(logDirectoryType).getAsLogDirectoryType(log);
        }
    }

    public List getByIDs(Collection<String> ids, LogDirectoryType logDirectoryType) throws Exception {
        if (logDirectoryType == null || logDirectoryType.equals(LogDirectoryType.DEFAULT)) {
            return logGetService.findByIds(ids);
        } else {
            List<FileModel> logs = logGetService.validateAndFindByIDs(ids);
            return getServiceByLogType(logDirectoryType).getAsLogDirectoryType(logs);
        }
    }

    public Page findAll(Pageable pageable, LogDirectoryType logDirectoryType) throws Exception {
        if (logDirectoryType == null || logDirectoryType.equals(LogDirectoryType.DEFAULT)) {
            return logGetService.findAll(pageable);
        } else {
            Page<FileModel> page = logGetService.findAll(pageable);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logDirectoryType).getAsLogDirectoryType(page.getContent()),
                    pageable,
                    page::getTotalElements);
        }
    }

    public Page theGetter(FileGetterRequest getterRequest, LogDirectoryType logDirectoryType) throws Exception {
        if (logDirectoryType == null || logDirectoryType.equals(LogDirectoryType.DEFAULT)) {
            return logGetService.theGetter(getterRequest);
        } else {
            Page<FileModel> page = logGetService.theGetter(getterRequest);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logDirectoryType).getAsLogDirectoryType(page.getContent()),
                    getterRequest.getPageable(),
                    page::getTotalElements);
        }
    }

    public FileModel getAsLogType(FileModel log, LogDirectoryType logDirectoryType) {
        return getServiceByLogType(logDirectoryType).getAsLogDirectoryType(log);
    }

    public List<FileModel> getAsLogType(Collection<FileModel> logs, LogDirectoryType logDirectoryType) {
        return getServiceByLogType(logDirectoryType).getAsLogDirectoryType(logs);
    }

    private ATypedLogDirectoryFileDataGetService getServiceByLogType(LogDirectoryType logDirectoryType) {
        ATypedLogDirectoryFileDataGetService getService = mapModel2GetService.get(logDirectoryType);
        Assert.notNull(getService, "LogTypeGetManagerService does not contain key: '" + logDirectoryType.toString() + "' available keys are: " + mapModel2GetService.keySet().toString());
        return getService;
    }
}
