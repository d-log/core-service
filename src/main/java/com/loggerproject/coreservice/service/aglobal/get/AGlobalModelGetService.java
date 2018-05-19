package com.loggerproject.coreservice.service.aglobal.get;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import com.loggerproject.coreservice.data.repository.GlobalModelRepository;
import com.loggerproject.coreservice.service.aglobal.AGlobalModelCrudService;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.aglobal.delete.AGlobalModelDeleteService;
import com.loggerproject.coreservice.service.aglobal.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.service.aglobal.get.model.PageSizeOverflowException;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AGlobalModelGetService<T extends GlobalModel> extends AGlobalModelCrudService<T> {

    @Value("${spring.data.rest.maxPageSize}")
    protected Integer MAX_PAGE_SIZE;

    protected Integer maxPageSize;
    protected Pageable defaultPageable;

    @PostConstruct
    protected void init() {
        if (maxPageSize == null) {
            maxPageSize = MAX_PAGE_SIZE;
        }
        if (defaultPageable == null) {
            defaultPageable = new PageRequest(0, MAX_PAGE_SIZE);
        }
    }

    public AGlobalModelGetService(GlobalModelRepository<T, String> repository,
                                  @Lazy AGlobalModelCreateService globalServerCreateService,
                                  @Lazy AGlobalModelDeleteService globalServerDeleteService,
                                  @Lazy AGlobalModelGetService globalServerGetService,
                                  @Lazy AGlobalModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public AGlobalModelGetService(GlobalModelRepository<T, String> repository,
                                  @Lazy AGlobalModelCreateService globalServerCreateService,
                                  @Lazy AGlobalModelDeleteService globalServerDeleteService,
                                  @Lazy AGlobalModelGetService globalServerGetService,
                                  @Lazy AGlobalModelUpdateService globalServerUpdateService,
                                  Integer maxPageSize) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        setup(maxPageSize, new PageRequest(0, maxPageSize));
    }

    public AGlobalModelGetService(GlobalModelRepository<T, String> repository,
                                  @Lazy AGlobalModelCreateService globalServerCreateService,
                                  @Lazy AGlobalModelDeleteService globalServerDeleteService,
                                  @Lazy AGlobalModelGetService globalServerGetService,
                                  @Lazy AGlobalModelUpdateService globalServerUpdateService,
                                  Integer maxPageSize,
                                  Pageable defaultPageable) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        setup(maxPageSize, defaultPageable);
    }

    private void setup(Integer maxPageSize, Pageable defaultPageable) {
        this.maxPageSize = maxPageSize;
        this.defaultPageable = defaultPageable;
    }

    public List<T> findByName(String name) {
        return repository.findByMetadata_name(name);
    }

    public Page<T> findByName(String name, Pageable pageable) throws Exception {
        pageable = scrubValidatePageable(pageable);
        return repository.findByMetadata_name(name, pageable);
    }

    public Page<T> findByNameLike(String nameLike, Pageable pageable) throws Exception {
        pageable = scrubValidatePageable(pageable);
        return repository.findByMetadata_nameLike(nameLike, pageable);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) throws Exception {
        pageable = scrubValidatePageable(pageable);
        return repository.findAll(pageable);
    }

    public T validateAndFindOne(String id) throws Exception {
        T t = findOne(id);
        if (t == null) {
            globalServerGetService.throwModelNotFoundException(id);
        }
        return t;
    }

    public List<T> validateAndFindByIDs(Collection<String> ids) throws Exception {
        List<T> models = new ArrayList<>();
        for (String id : ids) {
            T t = validateAndFindOne(id);
            models.add(t);
        }
        return models;
    }

    public void validateId(String id) throws Exception {
        if (findOne(id) == null) {
            globalServerGetService.throwModelNotFoundException(id);
        }
    }

    public void validateIds(Collection<String> ids) throws Exception {
        for (String id : ids) {
            validateId(id);
        }
    }

    // TODO findAll(Iterable<ID> ids)
    public List<T> findByIds(Collection<String> ids) {
        List<T> models = new ArrayList<>();
        for (String id : ids) {
            T t = findOne(id);
            models.add(t);
        }
        return models;
    }

    public T findOne(String id) {
        return repository.findOne(id);
    }

    public Boolean exists(String id) {
        return repository.exists(id);
    }

    protected void throwModelNotFoundException(String id) throws ModelNotFoundException {
        throw new ModelNotFoundException("non-existent " + genericClass.getSimpleName() + " ID: '" + id + "'");
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
