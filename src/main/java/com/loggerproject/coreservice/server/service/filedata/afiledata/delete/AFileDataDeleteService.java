package com.loggerproject.coreservice.server.service.filedata.afiledata.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.afiledata.AFileDataCrudService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model.ValidateDeleteModelException;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AFileDataDeleteService<T> extends AFileDataCrudService<T> {

    public AFileDataDeleteService(@Lazy AFileDataCreateService globalServerCreateService,
                                  @Lazy AFileDataDeleteService globalServerDeleteService,
                                  @Lazy AFileDataGetService globalServerGetService,
                                  @Lazy AFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel beforeDeleteValidate(FileModel t) throws Exception {
        return t;
    }

    protected FileModel beforeDelete(FileModel t) throws Exception {
        beforeDeleteValidate(t);
        return t;
    }

    protected FileModel afterDelete(FileModel t) throws Exception {
        return t;
    }

    public FileModel delete(String id) throws Exception {
        FileModel model = globalServerGetService.validateAndFindOne(id);
        return internalDelete(model);
    }

    private FileModel internalDelete(FileModel model) throws Exception {
        model = beforeDelete(model);
        repository.delete(model.getId());
        model = afterDelete(model);
        return model;
    }

    public DeleteAllResponse deleteAll() throws Exception {
        DeleteAllResponse response = new DeleteAllResponse();

        List<FileModel> models = globalServerGetService.findAll();
        for (FileModel model : models) {
            try {
                internalDelete(model);
                response.getDeletedIDs().add(model.getId());
            } catch (ValidateDeleteModelException e) {
                response.getNotDeletedIDs().add(model.getId());
                response.getReasons().put(model.getId(), e.getMessage());
            }
        }

        return response;
    }
}
