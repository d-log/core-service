package com.loggerproject.coreservice.server.service.filedata.afiledata.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.afiledata.AFileDataCrudService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;

import java.util.Date;

public abstract class AFileDataUpdateService<T> extends AFileDataCrudService<T> {

    public AFileDataUpdateService(AFileDataCreateService globalServerCreateService,
                                  AFileDataDeleteService globalServerDeleteService,
                                  AFileDataGetService globalServerGetService,
                                  AFileDataUpdateService globalServerUpdateService) {
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
    public FileModel beforeUpdateScrubAndValidate(FileModel t) throws Exception {
        return t;
    }

    @SuppressWarnings("unchecked")
    protected FileModel beforeUpdate(FileModel t) throws Exception {
        FileModel old = globalServerGetService.validateAndFindOne(t.getId());
        t.setMetadata(old.getMetadata());
        t.getMetadata().setLastUpdated(new Date());
        beforeUpdateScrubAndValidate(t);
        return t;
    }

    protected FileModel afterUpdate(FileModel t) throws Exception {
        return t;
    }

    public FileModel update(FileModel t) throws Exception {
        t = beforeUpdate(t);
        t = repository.save(t);
        dataConvertValue(t);
        t = afterUpdate(t);
        return t;
    }
}