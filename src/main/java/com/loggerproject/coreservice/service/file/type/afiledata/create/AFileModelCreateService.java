package com.loggerproject.coreservice.service.file.type.afiledata.create;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.service.file.type.afiledata.AFileModelCrudService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

@SuppressWarnings("unchecked")
public abstract class AFileModelCreateService<T> extends AFileModelCrudService<T> {

    public AFileModelCreateService(@Lazy AFileModelCreateService globalServerCreateService,
                                   @Lazy AFileModelDeleteService globalServerDeleteService,
                                   @Lazy AFileModelGetService globalServerGetService,
                                   @Lazy AFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    /**
     * Running this on the same object multiple times should be idempotent
     * with no changes in database
     *
     * @param t
     * @return
     * @throws Exception
     */
    public FileModel beforeSaveScrubAndValidate(FileModel t) throws Exception {
        return t;
    }

    protected FileModel beforeCreate(FileModel t) throws Exception {
        if (!genericClass.isInstance(t.getData())) {
            t.setData(objectMapper.convertValue(t.getData(), genericClass));
        }

        t = beforeSaveScrubAndValidate(t);

        if (t.getMetadata() == null) {
            t.setMetadata(new Metadata());
        }

        t.setId(null);
        Date date = new Date();
        t.getMetadata().setType(genericClass.getSimpleName());
        t.getMetadata().setLastUpdated(date);
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
    protected FileModel afterCreateUpdateOtherDocuments(FileModel t) throws Exception {
        return t;
    }

    /**
     * TODO exception handle
     *
     * @param t
     * @return
     * @throws Exception
     */
    protected FileModel afterCreate(FileModel t) throws Exception {
        t = afterCreateUpdateOtherDocuments(t);
        return t;
    }

    public FileModel create(FileModel t) throws Exception {
        t = beforeCreate(t);
        t = repository.save(t);
        t.setData(objectMapper.convertValue(t.getData(), genericClass));
        t = afterCreate(t);
        return t;
    }
}
