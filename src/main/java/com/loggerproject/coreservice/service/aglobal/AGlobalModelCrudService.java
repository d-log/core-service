package com.loggerproject.coreservice.service.aglobal;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import com.loggerproject.coreservice.data.repository.GlobalModelRepository;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.aglobal.delete.AGlobalModelDeleteService;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.GenericTypeResolver;

public abstract class AGlobalModelCrudService<T extends GlobalModel> {

    protected Class genericClass;
    protected GlobalModelRepository<T, String> repository;
    protected AGlobalModelCreateService globalServerCreateService;
    protected AGlobalModelDeleteService globalServerDeleteService;
    protected AGlobalModelGetService globalServerGetService;
    protected AGlobalModelUpdateService globalServerUpdateService;

    public AGlobalModelCrudService(GlobalModelRepository<T, String> repository,
                                   @Lazy AGlobalModelCreateService globalServerCreateService,
                                   @Lazy AGlobalModelDeleteService globalServerDeleteService,
                                   @Lazy AGlobalModelGetService globalServerGetService,
                                   @Lazy AGlobalModelUpdateService globalServerUpdateService) {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AGlobalModelCrudService.class);
        this.repository = repository;
        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }
}
