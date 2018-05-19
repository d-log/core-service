package com.loggerproject.coreservice.service.aglobal.delete;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import com.loggerproject.coreservice.data.repository.GlobalModelRepository;
import com.loggerproject.coreservice.service.aglobal.AGlobalModelCrudService;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.aglobal.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.service.aglobal.delete.model.ValidateDeleteModelException;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AGlobalModelDeleteService<T extends GlobalModel> extends AGlobalModelCrudService<T> {

    public AGlobalModelDeleteService(GlobalModelRepository<T, String> repository,
                                     @Lazy AGlobalModelCreateService globalServerCreateService,
                                     @Lazy AGlobalModelDeleteService globalServerDeleteService,
                                     @Lazy AGlobalModelGetService globalServerGetService,
                                     @Lazy AGlobalModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public T beforeDeleteValidate(T t) throws Exception {
        return t;
    }

    protected T beforeDelete(T t) throws Exception {
        beforeDeleteValidate(t);
        return t;
    }

    protected T afterDeleteUpdateOtherDocuments(T t) throws Exception {
        return t;
    }

    protected T afterDelete(T t) throws Exception {
        afterDeleteUpdateOtherDocuments(t);
        return t;
    }

    public T delete(String id) throws Exception {
        T t = (T) globalServerGetService.validateAndFindOne(id);
        return internalDelete(t);
    }

    private T internalDelete(T t) throws Exception {
        t = beforeDelete(t);
        repository.delete(t.getId());
        t = afterDelete(t);
        return t;
    }

    public DeleteAllResponse deleteAll() throws Exception {
        DeleteAllResponse response = new DeleteAllResponse();

        List<T> ts = globalServerGetService.findAll();
        for (T t : ts) {
            try {
                internalDelete(t);
                response.getDeletedIDs().add(t.getId());
            } catch (ValidateDeleteModelException e) {
                response.getNotDeletedIDs().add(t.getId());
                response.getReasons().put(t.getId(), e.getMessage());
            }
        }

        return response;
    }
}
