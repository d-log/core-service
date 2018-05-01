package com.loggerproject.coreservice.server.service.filedata.afiledata.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.server.service.filedata.afiledata.AFileDataCrudService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.GenericTypeResolver;

import java.util.Date;

@SuppressWarnings("unchecked")
public abstract class AFileDataCreateService<T> extends AFileDataCrudService<T> {

    public AFileDataCreateService(@Lazy AFileDataCreateService globalServerCreateService,
                                  @Lazy AFileDataDeleteService globalServerDeleteService,
                                  @Lazy AFileDataGetService globalServerGetService,
                                  @Lazy AFileDataUpdateService globalServerUpdateService) {
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

    protected FileModel afterCreate(FileModel t) throws Exception {
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
