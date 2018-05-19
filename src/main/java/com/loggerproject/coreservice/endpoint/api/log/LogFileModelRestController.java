package com.loggerproject.coreservice.endpoint.api.log;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.endpoint.api.AGlobalModelRestController;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.endpoint.api.log.model.UpdateBindUnbindRequest;
import com.loggerproject.coreservice.endpoint.api.log.model.UpdateLogContentsRequest;
import com.loggerproject.coreservice.service.FileGetterRequest;
import com.loggerproject.coreservice.service.log.RootLogModelService;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayTypeModelGetManagerService;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/file/log")
@SuppressWarnings(value = "unchecked")
public class LogFileModelRestController extends AGlobalModelRestController {

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    LogModelUpdateService logFileModelUpdateService;

    @Autowired
    LogDisplayTypeModelGetManagerService logTypeModelGetManagerService;

    @Autowired
    RootLogModelService rootLogModelService;

    @Autowired
    public LogFileModelRestController(LogModelCreateService globalServerCreateService,
                                      LogModelDeleteService globalServerDeleteService,
                                      LogModelGetService globalServerGetService,
                                      LogModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    /////////////
    // POSTERS //
    /////////////

    @PostMapping(value = "/root", produces = {"application/hal+json"})
    public ResponseEntity createRoot() throws Exception {
        LogModel root = rootLogModelService.createRoot();

        Resources resources = new EmptiableResources(LogModel.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).createRoot()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    /////////////
    // GETTERS //
    /////////////

    @GetMapping(value = "/the-getter", produces = {"application/hal+json"})
    public ResponseEntity theGetter(@RequestParam(value = "millisecond-threshold", required = false) Long millisecondThreshold,
                                    @RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "tag-id", required = false) String tagID,
                                    @RequestParam(value = "directory-id", required = false) String directoryID,
                                    @RequestParam(value = "log-type", required = false) LogDisplayType logType,
                                    Pageable pageable,
                                    PagedResourcesAssembler assembler) throws Exception {
        FileGetterRequest getterRequest = new FileGetterRequest();
        getterRequest.setTagID(tagID);
        getterRequest.setParentLogID(directoryID);
        getterRequest.setSearchString(search);
        getterRequest.setMillisecondThreshold(millisecondThreshold);
        getterRequest.setPageable(pageable);
        getterRequest.setLogType(logType);

        Page<LogModel> page = logTypeModelGetManagerService.theGetter(getterRequest, logType);
        Resources resources;
        if (page.getContent().size() == 0) {
            resources = assembler.toEmptyResource(page, LogModel.class, null);
        } else {
            resources = assembler.toResource(page);
        }
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/{id}/{log-type}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogType(@PathVariable("id") String id,
                                        @PathVariable("log-type") LogDisplayType logType) {
        ALogDisplayType model = logTypeModelGetManagerService.findOne(id, logType);
        Resources resources = new EmptiableResources(ALogDisplayType.class, Collections.singletonList(model));
        resources.add(linkTo(methodOn(getClass()).getLogType(id, logType)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/all/{log-type}", produces = "application/hal+json")
    public ResponseEntity<?> getAllLogType(@PathVariable("log-type") LogDisplayType logType,
                                           Pageable pageable,
                                           PagedResourcesAssembler assembler) throws Exception {
        Page page = logTypeModelGetManagerService.findAll(pageable, logType);
        Resources resources = assembler.toResource(page);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/root/{log-type}", produces = {"application/hal+json"})
    public ResponseEntity getRoot(@PathVariable("log-type") LogDisplayType logType) throws Exception {
        ALogDisplayType root = logTypeModelGetManagerService.getRoot(logType);

        Resources resources = new EmptiableResources(ALogDisplayType.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).getRoot(logType)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/root/children/{level}", "/root/children"}, produces = {"application/hal+json"})
    public ResponseEntity findChildrenOfRoot(@PathVariable(value = "level", required = false) Integer level) throws Exception {
        ALogDisplayType root = logTypeModelGetManagerService.getRoot(LogDisplayType.DEFAULT);
        List<LogModel> models = logModelGetService.findChildren(root.getId(), level);

        Resources resources = new EmptiableResources(LogModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildrenOfRoot(level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/children/{level}", "/{id}/children"}, produces = {"application/hal+json"})
    public ResponseEntity findChildren(@PathVariable("id") String id, @PathVariable(value = "level", required = false) Integer level) throws Exception {
        List<LogModel> models = logModelGetService.findChildren(id, level);

        Resources resources = new EmptiableResources(LogModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildren(id, level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/parent"}, produces = {"application/hal+json"})
    public ResponseEntity findParent(@PathVariable("id") String id) throws Exception {
        List<LogModel> models = logModelGetService.findParents(id);

        Resources resources = new EmptiableResources(LogModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findParent(id)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    //////////////
    // UPDATERS //
    //////////////

    @PutMapping(produces = {"application/hal+json"})
    public ResponseEntity<?> updateWholeModelAndSyncOtherDocuments(@RequestBody LogModel model) throws Exception {
        LogModel modelUpdated = logFileModelUpdateService.updateWholeModelAndSyncOtherDocuments(model);
        return hateosBuilder(modelUpdated, methodOn(getClass()).updateWholeModelAndSyncOtherDocuments(model));
    }


    @PutMapping(value = {"/bind-unbind/tags"}, produces = {"application/hal+json"})
    public ResponseEntity<?> bindUnbindTags(@RequestBody UpdateBindUnbindRequest request) throws Exception {
        LogModel modelUpdated = logFileModelUpdateService.bindUnbindTags(request.getLogID(), request.getBindModelIDs(), request.getUnbindModelIDs());
        return hateosBuilder(modelUpdated, methodOn(getClass()).bindUnbindTags(request));
    }

    @PutMapping(value = {"/bind-unbind/parent-logs"}, produces = {"application/hal+json"})
    public ResponseEntity<?> bindUnbindParentLogs(@RequestBody UpdateBindUnbindRequest request) throws Exception {
        LogModel modelUpdated = logFileModelUpdateService.bindUnbindParentLogs(request.getLogID(), request.getBindModelIDs(), request.getUnbindModelIDs());
        return hateosBuilder(modelUpdated, methodOn(getClass()).bindUnbindParentLogs(request));
    }

    @PutMapping(value = {"/log-contents"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateLogContents(@RequestBody UpdateLogContentsRequest request) throws Exception {
        LogModel modelUpdated = logFileModelUpdateService.updateLogContents(request.getId(), request.getLogContents());
        return hateosBuilder(modelUpdated, methodOn(getClass()).updateLogContents(request));
    }

    private ResponseEntity<?> hateosBuilder(LogModel model, Object invocationValue) {
        Resources<LogModel> resources = new EmptiableResources(LogModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}