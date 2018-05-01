package com.loggerproject.coreservice.server.service.filedata.afiledata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.GenericTypeResolver;

import java.util.List;

@SuppressWarnings(value = "unchecked")
public abstract class AFileDataCrudService<T> {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected FileModelRepository repository;

    protected Class genericClass;
    protected AFileDataCreateService globalServerCreateService;
    protected AFileDataDeleteService globalServerDeleteService;
    protected AFileDataGetService globalServerGetService;
    protected AFileDataUpdateService globalServerUpdateService;

    public AFileDataCrudService(@Lazy AFileDataCreateService globalServerCreateService,
                                @Lazy AFileDataDeleteService globalServerDeleteService,
                                @Lazy AFileDataGetService globalServerGetService,
                                @Lazy AFileDataUpdateService globalServerUpdateService) {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AFileDataCrudService.class);
        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }

    protected void dataConvertValue(List<FileModel> models) {
        for (FileModel model : models) {
            dataConvertValue(model);
        }
    }

    protected void dataConvertValue(FileModel model) {
        model.setData(objectMapper.convertValue(model.getData(), genericClass));
    }
}
