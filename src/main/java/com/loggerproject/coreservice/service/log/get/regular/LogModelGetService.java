package com.loggerproject.coreservice.service.log.get.regular;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.FileGetterRequest;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.log.RootLogModelService;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogModelGetService extends AGlobalModelGetService<LogModel> {

    @Autowired
    RootLogModelService rootLogModelService;

    @Autowired
    public LogModelGetService(LogModelRepository repository,
                              @Lazy LogModelCreateService globalServerCreateService,
                              @Lazy LogModelDeleteService globalServerDeleteService,
                              @Lazy LogModelGetService globalServerGetService,
                              @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public Page<LogModel> theGetter(FileGetterRequest getterrequest) {
        return null;
    }

    public LogModel getRoot() throws Exception {
        return rootLogModelService.getRoot();
    }

    public List<LogModel> findChildren(String id) throws Exception {
        return findChildren(id, 1);
    }

    /**
     * @param id
     * @param level - if null then default 1
     * @return FileModel[]
     * @throws Exception
     */
    public List<LogModel> findChildren(String id, Integer level) throws Exception {
        level = level == null ? 1 : level;
        List<LogModel> children = new ArrayList<>();

        if (level > 0) {
            LogModel directory = validateAndFindOne(id);
            Set<String> childrenIDs = directory.getLogOrganization().getChildLogIDs();

            for (String childID : childrenIDs) {
                LogModel child = findOne(childID);
                if (child != null) {
                    children.add(child);
                }
            }

            List<LogModel> grandChildren = new ArrayList<>();
            for (LogModel child : children) {
                grandChildren.addAll(findChildren(child.getId(), level - 1));
            }
            children.addAll(grandChildren);
        }

        return children;
    }

    public List<LogModel> findParents(String id) throws Exception {
        LogModel directory = validateAndFindOne(id);
        Set<String> parentIDs = directory.getLogOrganization().getParentLogIDs();
        return findByIds(parentIDs);
    }
}
