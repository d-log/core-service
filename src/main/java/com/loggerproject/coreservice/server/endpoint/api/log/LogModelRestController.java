package com.loggerproject.coreservice.server.endpoint.api.log;

import com.loggerproject.coreservice.global.server.endpoint.api.EmptiableResources;
import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.endpoint.api.log.model.UpdateBindUnbindRequest;
import com.loggerproject.coreservice.server.endpoint.api.log.model.UpdateLogDatasRequest;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/log")
public class LogModelRestController extends GlobalModelController<LogModel> {

    @Autowired
    LogModelUpdateService logModelUpdateService;

    @Autowired
    public LogModelRestController(LogModelCreateService globalServerCreateService,
                                  LogModelDeleteService globalServerDeleteService,
                                  LogModelGetService globalServerGetService,
                                  LogModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @PutMapping(value = {"/bind-unbind/tags"}, produces = {"application/hal+json"})
    public ResponseEntity<?> bindUnbindTag(@RequestBody UpdateBindUnbindRequest request) throws Exception {
        LogModel modelUpdated = logModelUpdateService.bindUnbindTags(request.getLogID(), request.getBindModelIDs(), request.getUnbindModelIDs());
        return hateosBuilder(modelUpdated, methodOn(getClass()).bindUnbindTag(request));
    }

    @PutMapping(value = {"/bind-unbind/directories"}, produces = {"application/hal+json"})
    public ResponseEntity<?> bindDirectory(@RequestBody UpdateBindUnbindRequest request) throws Exception {
        LogModel modelUpdated = logModelUpdateService.bindUnbindDirectories(request.getLogID(), request.getBindModelIDs(), request.getUnbindModelIDs());
        return hateosBuilder(modelUpdated, methodOn(getClass()).bindDirectory(request));
    }

    @PutMapping(value = {"/log-datas"}, produces = {"application/hal+json"})
    public ResponseEntity<?> bindDirectory(@RequestBody UpdateLogDatasRequest request) throws Exception {
        LogModel modelUpdated = logModelUpdateService.updateLogDatas(request.getId(), request.getALogData());
        return hateosBuilder(modelUpdated, methodOn(getClass()).bindDirectory(request));
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosBuilder(LogModel model, Object invocationValue) {
        Resources<LogModel> resources = new EmptiableResources(LogModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}
