package com.loggerproject.coreservice.service.aglobal.update;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import com.loggerproject.coreservice.data.repository.GlobalModelRepository;
import com.loggerproject.coreservice.service.aglobal.AGlobalModelCrudService;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.aglobal.delete.AGlobalModelDeleteService;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;

import java.util.Date;

public abstract class AGlobalModelUpdateService<T extends GlobalModel> extends AGlobalModelCrudService<T> {

    public AGlobalModelUpdateService(GlobalModelRepository<T, String> repository,
                                     AGlobalModelCreateService globalServerCreateService,
                                     AGlobalModelDeleteService globalServerDeleteService,
                                     AGlobalModelGetService globalServerGetService,
                                     AGlobalModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    /**
     * Running this on the same object multiple times should be idempotent
     * with no changes in database
     *
     * @param t
     * @return
     * @throws Exception
     */
    public T beforeUpdateScrubAndValidate(T t) throws Exception {
        return t;
    }

    @SuppressWarnings("unchecked")
    protected T beforeUpdate(T t) throws Exception {
        t.getMetadata().setLastUpdated(new Date());
        beforeUpdateScrubAndValidate(t);
        return t;
    }

    protected T afterUpdate(T t) throws Exception {
        return t;
    }

    public T update(T t) throws Exception {
        t = beforeUpdate(t);
        t = repository.save(t);
        t = afterUpdate(t);
        return t;
    }
}
