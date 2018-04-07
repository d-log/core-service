package com.loggerproject.coreservice.global.server.service.delete;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.global.server.service.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.global.server.service.delete.model.ValidateDeleteModelException;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class GlobalServerDeleteService<T extends GlobalModel> {

    protected MongoRepository<T, String> repository;
    protected GlobalServerCreateService globalServerCreateService;
    protected GlobalServerDeleteService globalServerDeleteService;
    protected GlobalServerGetService globalServerGetService;
    protected GlobalServerUpdateService globalServerUpdateService;

    public GlobalServerDeleteService(MongoRepository<T, String> repository,
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

    protected T beforeDelete(T t) throws Exception {
        return t;
    }

    protected T afterDelete(T t) throws Exception {
        return t;
    }

    public T delete(String id) throws Exception {
        T t = (T)globalServerGetService.validateAndFindOne(id);
        t = beforeDelete(t);
        repository.delete(id);
        t = afterDelete(t);
        return t;
    }

    public DeleteAllResponse deleteAll() throws Exception {
        DeleteAllResponse response = new DeleteAllResponse();

        List<T> models = repository.findAll();
        for (T model : models) {
            try {
                delete(model.getID());
                response.getDeletedIDs().add(model.getID());
            }
            catch(ValidateDeleteModelException e) {
                response.getNotDeletedIDs().add(model.getID());
                response.getReasons().put(model.getID(), e.getMessage());
            }
        }

        return response;
    }
}
