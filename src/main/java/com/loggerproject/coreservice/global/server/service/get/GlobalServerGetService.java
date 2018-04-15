package com.loggerproject.coreservice.global.server.service.get;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.global.server.service.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.global.server.service.get.model.PageSizeOverflowException;
import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class GlobalServerGetService<T extends GlobalModel> {

    protected Class<T> genericType;
    protected String genericName;

    protected MongoRepository<T, String> repository;
    protected GlobalServerCreateService globalServerCreateService;
    protected GlobalServerDeleteService globalServerDeleteService;
    protected GlobalServerGetService globalServerGetService;
    protected GlobalServerUpdateService globalServerUpdateService;

    protected Integer maxPageSize;
    protected Pageable defaultPageable;

    public GlobalServerGetService(MongoRepository<T, String> repository,
                                  GlobalServerCreateService globalServerCreateService,
                                  GlobalServerDeleteService globalServerDeleteService,
                                  GlobalServerGetService globalServerGetService,
                                  GlobalServerUpdateService globalServerUpdateService,
                                  Integer maxPageSize) {
        Pageable defaultPageable = new PageRequest(0, maxPageSize);
        setup(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService, maxPageSize, defaultPageable);
    }

    public GlobalServerGetService(MongoRepository<T, String> repository,
                                  GlobalServerCreateService globalServerCreateService,
                                  GlobalServerDeleteService globalServerDeleteService,
                                  GlobalServerGetService globalServerGetService,
                                  GlobalServerUpdateService globalServerUpdateService,
                                  Integer maxPageSize,
                                  Pageable defaultPageable) {
        setup(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService, maxPageSize, defaultPageable);
    }

    private void setup(MongoRepository<T, String> repository,
                       GlobalServerCreateService globalServerCreateService,
                       GlobalServerDeleteService globalServerDeleteService,
                       GlobalServerGetService globalServerGetService,
                       GlobalServerUpdateService globalServerUpdateService,
                       Integer maxPageSize,
                       Pageable defaultPageable) {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GlobalServerGetService.class);
        this.genericName = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();

        this.repository = repository;
        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;

        this.maxPageSize = maxPageSize;
        this.defaultPageable = defaultPageable;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) throws Exception {
        pageable = scrubValidatePageable(pageable);
        return repository.findAll(pageable);
    }

    public T validateAndFindOne(String id) throws Exception {
        Assert.notNull(id, genericName + " id cannot be null");
        T t = findOne(id);
        if (t == null) {
            globalServerGetService.throwModelNotFoundException(id);
        }
        return t;
    }

    public List<T> validateAndFindByIDs(Collection<String> ids) throws Exception {
        Assert.notNull(ids, genericName + " ids cannot be null");
        List<T> models = new ArrayList<>();
        for (String id : ids) {
            T t = validateAndFindOne(id);
            models.add(t);
        }
        return models;
    }

    public void validateId(String id) throws Exception {
        Assert.notNull(id, genericName + " id cannot be null");
        T t = findOne(id);
        if (t == null) {
            globalServerGetService.throwModelNotFoundException(id);
        }
    }

    public void validateIds(Collection<String> ids) throws Exception {
        Assert.notNull(ids, genericName + " ids cannot be null");
        for (String id : ids) {
            validateId(id);
        }
    }

    public List<T> findByIds(Collection<String> ids) {
        Assert.notNull(ids, genericName + " ids cannot be null");
        List<T> models = new ArrayList<>();
        for (String id : ids) {
            T t = findOne(id);
            models.add(t);
        }
        return models;
    }

    public T findOne(String id) {
        Assert.notNull(id, genericName + " id cannot be null");
        return repository.findOne(id);
    }

    public Boolean exists(String id) {
        return repository.exists(id);
    }

    protected void throwModelNotFoundException(String id) throws ModelNotFoundException {
        throw new ModelNotFoundException("non-existent " + genericName + " ID: '" + id + "'");
    }

    protected Pageable scrubValidatePageable(Pageable pageable) throws Exception {
        if (pageable != null) {
            if (pageable.getPageSize() > maxPageSize) {
                throwPageSizeOverflowException(pageable.getPageSize());
            }
        } else {
            pageable = defaultPageable;
        }

        return pageable;
    }

    protected void throwPageSizeOverflowException(Integer pageSize) throws PageSizeOverflowException {
        throw new PageSizeOverflowException("PageSize cannot be greater than " + maxPageSize + " - page size received: '" + pageSize + "'");
    }
}
