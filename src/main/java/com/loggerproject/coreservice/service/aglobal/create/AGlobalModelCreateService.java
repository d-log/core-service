package com.loggerproject.coreservice.service.aglobal.create;

import com.loggerproject.coreservice.data.model._shared.IGlobalModel;
import com.loggerproject.coreservice.data.model._shared.Metadata;
import com.loggerproject.coreservice.data.repository.GlobalModelRepository;
import com.loggerproject.coreservice.service.aglobal.AGlobalModelCrudService;
import com.loggerproject.coreservice.service.aglobal.delete.AGlobalModelDeleteService;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

@SuppressWarnings("unchecked")
public abstract class AGlobalModelCreateService<T extends IGlobalModel> extends AGlobalModelCrudService<T> {

    public AGlobalModelCreateService(GlobalModelRepository<T, String> repository,
                                     @Lazy AGlobalModelCreateService globalServerCreateService,
                                     @Lazy AGlobalModelDeleteService globalServerDeleteService,
                                     @Lazy AGlobalModelGetService globalServerGetService,
                                     @Lazy AGlobalModelUpdateService globalServerUpdateService) {
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
    public T beforeSaveScrubAndValidate(T t) throws Exception {
        return t;
    }

    protected T beforeCreate(T t) throws Exception {
        t = beforeSaveScrubAndValidate(t);

        if (t.getMetadata() == null) {
            t.setMetadata(new Metadata());
        }

        t.setId(null);
        Date date = new Date();
        t.getMetadata().setCreated(date);
        return t;
    }

    /**
     * TODO exception handle
     *
     * @param t
     * @return
     * @throws Exception
     */
    protected T afterCreateUpdateOtherDocuments(T t) throws Exception {
        return t;
    }

    /**
     * TODO exception handle
     *
     * @param t
     * @return
     * @throws Exception
     */
    protected T afterCreate(T t) throws Exception {
        t = afterCreateUpdateOtherDocuments(t);
        return t;
    }

    public T create(T t) throws Exception {
        t = beforeCreate(t);
        t = repository.save(t);
        t = afterCreate(t);
        return t;
    }
}
