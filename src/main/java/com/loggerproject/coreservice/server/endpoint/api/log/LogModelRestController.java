package com.loggerproject.coreservice.server.endpoint.api.log;

import com.loggerproject.coreservice.global.server.endpoint.api.EmptiableResources;
import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.endpoint.api.log.model.UpdateBindUnbindRequest;
import com.loggerproject.coreservice.server.endpoint.api.log.model.UpdateLogDatasRequest;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogTypeGetManagerService;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/log")
public class LogModelRestController extends GlobalModelController<LogModel> {

    @Autowired
    LogModelUpdateService logModelUpdateService;

    @Autowired
    LogTypeGetManagerService logTypeGetManagerService;

    @Autowired
    public LogModelRestController(LogModelCreateService globalServerCreateService,
                                  LogModelDeleteService globalServerDeleteService,
                                  LogModelGetService globalServerGetService,
                                  LogModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @GetMapping(value = "/{id}/{log-type}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogType(@PathVariable("id") String id, @PathVariable("log-type") LogType logType) throws Exception {
        ALogTypeModel logTypeModel = logTypeGetManagerService.getByID(id, logType);
        Resources resources = new EmptiableResources(genericType, Collections.singletonList(logTypeModel));
        resources.add(linkTo(methodOn(getClass()).getLogType(id, logType)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/all/{log-type}", produces="application/hal+json")
    public ResponseEntity<?> getAllLogType(@PathVariable("log-type") LogType logType) throws Exception {
        List<LogModel> logs = globalServerGetService.findAll();
        List<ALogTypeModel> logTypes = logTypeGetManagerService.getByLogModels(logs, logType);
        Resources resources = new EmptiableResources(genericType, logTypes);
        resources.add(linkTo(methodOn(getClass()).getAllLogType(logType)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
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
        LogModel modelUpdated = logModelUpdateService.updateLogDatas(request.getId(), request.getLogDatas());
        return hateosBuilder(modelUpdated, methodOn(getClass()).bindDirectory(request));
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosBuilder(LogModel model, Object invocationValue) {
        Resources<LogModel> resources = new EmptiableResources(LogModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}
