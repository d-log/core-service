package com.loggerproject.coreservice.server.service.filedata.afiledata.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.afiledata.AFileDataCrudService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.model.PageSizeOverflowException;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AFileDataGetService<T> extends AFileDataCrudService<T> {

//    @Value("${spring.data.rest.maxPageSize}")
//    private Integer MAX_PAGE_SIZE;

    protected Integer maxPageSize;
    protected Pageable defaultPageable;

    public AFileDataGetService(@Lazy AFileDataCreateService globalServerCreateService,
                               @Lazy AFileDataDeleteService globalServerDeleteService,
                               @Lazy AFileDataGetService globalServerGetService,
                               @Lazy AFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        setup(20, new PageRequest(0, 20));
    }

    public AFileDataGetService(@Lazy AFileDataCreateService globalServerCreateService,
                               @Lazy AFileDataDeleteService globalServerDeleteService,
                               @Lazy AFileDataGetService globalServerGetService,
                               @Lazy AFileDataUpdateService globalServerUpdateService,
                               Integer maxPageSize) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        setup(maxPageSize, new PageRequest(0, maxPageSize));
    }

    public AFileDataGetService(@Lazy AFileDataCreateService globalServerCreateService,
                               @Lazy AFileDataDeleteService globalServerDeleteService,
                               @Lazy AFileDataGetService globalServerGetService,
                               @Lazy AFileDataUpdateService globalServerUpdateService,
                               Integer maxPageSize,
                               Pageable defaultPageable) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        setup(maxPageSize, defaultPageable);
    }

    private void setup(Integer maxPageSize, Pageable defaultPageable) {
        this.maxPageSize = maxPageSize;
        this.defaultPageable = defaultPageable;
    }

    public List<FileModel> findByName(String name) {
        return repository.findByMetadata_typeAndMetadata_name(genericClass.getSimpleName(), name);
    }

    public List<FileModel> findAll() {
        List<FileModel> models = repository.findByMetadata_type(genericClass.getSimpleName());
        dataConvertValue(models);
        return models;
    }

    public Page<FileModel> findAll(Pageable pageable) throws Exception {
        pageable = scrubValidatePageable(pageable);
        Page<FileModel> page = repository.findByMetadata_type(genericClass.getSimpleName(), pageable);
        dataConvertValue(page.getContent());
        return page;
    }

    public FileModel validateAndFindOne(String id) throws Exception {
        FileModel t = findOne(id);
        if (t == null) {
            globalServerGetService.throwModelNotFoundException(id);
        }
        return t;
    }

    public List<FileModel> validateAndFindByIDs(Collection<String> ids) throws Exception {
        List<FileModel> models = new ArrayList<>();
        for (String id : ids) {
            FileModel t = validateAndFindOne(id);
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

    // findAll(Iterable<ID> ids)
    public List<FileModel> findByIds(Collection<String> ids) {
        List<FileModel> models = new ArrayList<>();
        for (String id : ids) {
            FileModel t = findOne(id);
            models.add(t);
        }
        return models;
    }

    public FileModel findOne(String id) {
        FileModel model = repository.findOne(id);
        if (model == null || !model.getMetadata().getType().equals(this.genericClass.getSimpleName())) {
            return null;
        }
        dataConvertValue(model);
        return model;
    }

    public Boolean exists(String id) {
        return findOne(id) != null;
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
