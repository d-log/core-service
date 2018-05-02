package com.loggerproject.coreservice.service.file.type.afiledata.delete;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.service.file.type.afiledata.AFileModelCrudService;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.model.ValidateDeleteModelException;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AFileModelDeleteService<T> extends AFileModelCrudService<T> {

    public AFileModelDeleteService(@Lazy AFileModelCreateService globalServerCreateService,
                                   @Lazy AFileModelDeleteService globalServerDeleteService,
                                   @Lazy AFileModelGetService globalServerGetService,
                                   @Lazy AFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel beforeDeleteValidate(FileModel t) throws Exception {
        return t;
    }

    protected FileModel beforeDelete(FileModel t) throws Exception {
        beforeDeleteValidate(t);
        return t;
    }

    protected FileModel afterDeleteUpdateOtherDocuments(FileModel t) throws Exception {
        return t;
    }

    protected FileModel afterDelete(FileModel t) throws Exception {
        afterDeleteUpdateOtherDocuments(t);
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
