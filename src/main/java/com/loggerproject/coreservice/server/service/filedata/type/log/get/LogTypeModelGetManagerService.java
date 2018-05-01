package com.loggerproject.coreservice.server.service.filedata.type.log.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.LogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.getter.model.GetterRequest;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeModel;
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
    LogFileDataGetService logGetService;

    @Autowired
    public LogTypeModelGetManagerService(List<ALogTypeGetService> logTypeGetServices) throws Exception {
        for (ALogTypeGetService logTypeGetService : logTypeGetServices) {
            ALogTypeModel logTypeModel = (ALogTypeModel) GenericTypeResolver.resolveTypeArgument(logTypeGetService.getClass(), ALogTypeGetService.class).newInstance();
            mapModel2GetService.put(logTypeModel.getLogType(), logTypeGetService);
        }
    }

    /**
     *
     * @param id
     * @param logType
     * @return FileModel | ALogModelTypeModel
     * @throws Exception
     */
    public Object validateAndFindOne(String id, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.validateAndFindOne(id);
        } else {
            FileModel log = logGetService.validateAndFindOne(id);
            return getServiceByLogType(logType).getByLogModel(log);
        }
    }

    public Object findOne(String id, LogType logType) {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.findOne(id);
        } else {
            FileModel log = logGetService.findOne(id);
            return getServiceByLogType(logType).getByLogModel(log);
        }
    }

    public List getByIDs(Collection<String> ids, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.findByIds(ids);
        } else {
            List<FileModel> logs = logGetService.validateAndFindByIDs(ids);
            return getServiceByLogType(logType).getByLogModels(logs);
        }
    }

    public Page findAll(Pageable pageable, LogType logType) throws Exception {
        if (logType == null || logType.equals(LogType.DEFAULT)) {
            return logGetService.findAll(pageable);
        } else {
            Page<FileModel> page = logGetService.findAll(pageable);
            return PageableExecutionUtils.getPage(
                    getServiceByLogType(logType).getByLogModels(page.getContent()),
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
                    getServiceByLogType(logType).getByLogModels(page.getContent()),
                    getterRequest.getPageable(),
                    page::getTotalElements);
        }
    }

    public ALogTypeModel getByLogModel(FileModel log, LogType logType) {
        return getServiceByLogType(logType).getByLogModel(log);
    }

    public List<ALogTypeModel> getByLogModels(Collection<FileModel> logs, LogType logType) {
        return getServiceByLogType(logType).getByLogModels(logs);
    }

    private ALogTypeGetService getServiceByLogType(LogType logType) {
        ALogTypeGetService getService = mapModel2GetService.get(logType);
        Assert.notNull(getService, "LogTypeGetManagerService does not contain key: '" + logType.toString() + "' available keys are: " + mapModel2GetService.keySet().toString());
        return getService;
    }
}
