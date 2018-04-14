package com.loggerproject.coreservice.global.server.service.update;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public abstract class GlobalServerUpdateService<T extends GlobalModel> {

    protected MongoRepository<T, String> repository;
    protected GlobalServerCreateService globalServerCreateService;
    protected GlobalServerDeleteService globalServerDeleteService;
    protected GlobalServerGetService globalServerGetService;
    protected GlobalServerUpdateService globalServerUpdateService;

    public GlobalServerUpdateService(MongoRepository<T, String> repository,
                                     GlobalServerCreateService globalServerCreateService,
                                     GlobalServerDeleteService globalServerDeleteService,
                                     GlobalServerGetService globalServerGetService,
                                     GlobalServerUpdateService globalServerUpdateService) {
        this.repository = repository;
        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }

    @SuppressWarnings("unchecked")
    protected T beforeUpdate(T t) throws Exception {
        T old = (T)this.globalServerGetService.validateAndFindOne(t.getID());
        t.setMetadata(old.getMetadata());
        t.getMetadata().setLastUpdated(new Date());
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
