package com.loggerproject.coreservice.server.endpoint.api.log;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.global.server.endpoint.api.EmptiableResources;
import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.endpoint.api.log.model.UpdateBindUnbindRequest;
import com.loggerproject.coreservice.server.endpoint.api.log.model.UpdateLogDatasRequest;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.get.LogType;
import com.loggerproject.coreservice.server.service.data.log.get.LogTypeModelGetManagerService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/log")
@SuppressWarnings(value = "unchecked")
public class LogModelRestController extends GlobalModelController<LogModel> {

    @Autowired
    LogModelUpdateService logModelUpdateService;

    @Autowired
    LogTypeModelGetManagerService logTypeModelGetManagerService;

    @Autowired
    public LogModelRestController(LogModelCreateService globalServerCreateService,
                                  LogModelDeleteService globalServerDeleteService,
                                  LogModelGetService globalServerGetService,
                                  LogModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    /////////////
    // GETTERS //
    /////////////

    @GetMapping(value = "/the-getter", produces = {"application/hal+json"})
    public ResponseEntity<?> theGetter(@RequestParam(value = "millisecond-threshold", required = false) Long millisecondThreshold,
                                       @RequestParam(value = "search", required = false) String search,
                                       @RequestParam(value = "tag-id", required = false) String tagID,
                                       @RequestParam(value = "directory-id", required = false) String directoryID,
                                       Pageable pageable,
                                       PagedResourcesAssembler assembler) throws Exception {
        return theGetterHelper(tagID, directoryID, search, millisecondThreshold, pageable, null, assembler);
    }

    @GetMapping(value = "/the-getter/{log-type}", produces = {"application/hal+json"})
    public ResponseEntity<?> theGetterLogType(@PathVariable("log-type") LogType logType,
                                              @RequestParam(value = "millisecond-threshold", required = false) Long millisecondThreshold,
                                              @RequestParam(value = "search", required = false) String search,
                                              @RequestParam(value = "tag-id", required = false) String tagID,
                                              @RequestParam(value = "directory-id", required = false) String directoryID,
                                              Pageable pageable,
                                              PagedResourcesAssembler assembler) throws Exception {
        return theGetterHelper(tagID, directoryID, search, millisecondThreshold, pageable, logType, assembler);
    }

    private ResponseEntity<?> theGetterHelper(String tagID, String directoryID, String search, Long millisecondThreshold, Pageable pageable, LogType logType, PagedResourcesAssembler assembler) throws Exception {
        GetterRequest getterRequest = new GetterRequest();
        getterRequest.setTagID(tagID);
        getterRequest.setDirectoryID(directoryID);
        getterRequest.setSearchString(search);
        getterRequest.setMillisecondThreshold(millisecondThreshold);
        getterRequest.setPageable(pageable);
        Page<ALogTypeModel> page = logTypeModelGetManagerService.theGetter(getterRequest, logType);
        Resources resources;
        if (page.getContent().size() == 0) {
            resources = assembler.toEmptyResource(page, ALogTypeModel.class, null);
        } else {
            resources = assembler.toResource(page);
        }
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/{id}/{log-type}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogType(@PathVariable("id") String id, @PathVariable("log-type") LogType logType) throws Exception {
        GlobalModel model = logTypeModelGetManagerService.findOne(id, logType);
        Resources resources = new EmptiableResources(genericType, Collections.singletonList(model));
        resources.add(linkTo(methodOn(getClass()).getLogType(id, logType)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/all/{log-type}", produces = "application/hal+json")
    public ResponseEntity<?> getAllLogType(@PathVariable("log-type") LogType logType, Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
        Page page = logTypeModelGetManagerService.findAll(pageable, logType);
        Resources resources = assembler.toResource(page);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    //////////////
    // UPDATERS //
    //////////////

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

    private ResponseEntity<?> hateosBuilder(LogModel model, Object invocationValue) {
        Resources<LogModel> resources = new EmptiableResources(LogModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}
