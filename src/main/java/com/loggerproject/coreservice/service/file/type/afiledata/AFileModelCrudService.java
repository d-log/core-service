package com.loggerproject.coreservice.service.file.type.afiledata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.repository.FileModelRepository;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.GenericTypeResolver;

import java.util.List;

@SuppressWarnings(value = "unchecked")
public abstract class AFileModelCrudService<T> {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected FileModelRepository repository;

    protected Class genericClass;
    protected AFileModelCreateService globalServerCreateService;
    protected AFileModelDeleteService globalServerDeleteService;
    protected AFileModelGetService globalServerGetService;
    protected AFileModelUpdateService globalServerUpdateService;

    public AFileModelCrudService(@Lazy AFileModelCreateService globalServerCreateService,
                                 @Lazy AFileModelDeleteService globalServerDeleteService,
                                 @Lazy AFileModelGetService globalServerGetService,
                                 @Lazy AFileModelUpdateService globalServerUpdateService) {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AFileModelCrudService.class);
        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }

    protected void convertFileDataObject2Generic(List<FileModel> models) {
        for (FileModel model : models) {
            convertFileDataObject2Generic(model);
        }
    }

    protected void convertFileDataObject2Generic(FileModel model) {
        model.setData(objectMapper.convertValue(model.getData(), genericClass));
    }
}
