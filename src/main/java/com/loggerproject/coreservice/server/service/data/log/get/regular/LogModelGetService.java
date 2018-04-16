package com.loggerproject.coreservice.server.service.data.log.get.regular;

import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.repository.LogModelRepository;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.LogModelGetterService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LogModelGetService extends GlobalServerGetService<LogModel> {

    @Autowired
    LogModelGetterService logModelGetterService;

    @Autowired
    public LogModelGetService(LogModelRepository repository,
                              @Lazy LogModelCreateService globalServerCreateService,
                              @Lazy LogModelDeleteService globalServerDeleteService,
                              @Lazy LogModelGetService globalServerGetService,
                              @Lazy LogModelUpdateService globalServerUpdateService,
                              @Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        super(repository,
                globalServerCreateService,
                globalServerDeleteService,
                globalServerGetService,
                globalServerUpdateService,
                maxPageSize);
    }

    public Page<LogModel> theGetter(GetterRequest getterRequest) throws Exception {
        getterRequest.setPageable(scrubValidatePageable(getterRequest.getPageable()));
        return logModelGetterService.theGetter(getterRequest);
    }
}
