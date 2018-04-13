package com.loggerproject.coreservice.global.server.service.create;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.global.server.document.model.MetaData;
import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

@SuppressWarnings("unchecked")
public abstract class GlobalServerCreateService<T extends GlobalModel> {

    protected MongoRepository<T, String> repository;
    protected GlobalServerCreateService globalServerCreateService;
    protected GlobalServerDeleteService globalServerDeleteService;
    protected GlobalServerGetService globalServerGetService;
    protected GlobalServerUpdateService globalServerUpdateService;

    public GlobalServerCreateService(MongoRepository<T, String> repository,
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

    /**
     * Running this on the same extra should be idempotent
     * @param t
     * @return
     * @throws Exception
     */
    public T beforeSaveScrubAndValidate(T t) throws Exception {
        return t;
    }

    protected T beforeCreate(T t) throws Exception {
        t = beforeSaveScrubAndValidate(t);
        t.setID(null);
        Date now = new Date();
        t.setMetadata(new MetaData(now, now));
        return t;
    }

    protected T afterCreate(T t) throws Exception {
        return t;
    }

    public T create(T t) throws Exception {
        t = beforeCreate(t);
        t = repository.save(t);
        t = afterCreate(t);
        return t;
    }
}
