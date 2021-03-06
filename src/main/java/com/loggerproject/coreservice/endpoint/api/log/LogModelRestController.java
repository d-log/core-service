package com.loggerproject.coreservice.endpoint.api.log;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.endpoint.api.AGlobalModelRestController;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.service.log.RootLogModelService;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayTypeModelGetManagerService;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.get.regular.extra.LogGetterRequest;
import com.loggerproject.coreservice.service.log.get.type.forupdate.ForUpdateLogModel;
import com.loggerproject.coreservice.service.log.get.type.page.PageLogModel;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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
@SuppressWarnings(value = "unchecked")
public class LogModelRestController extends AGlobalModelRestController<LogModel> {

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    LogModelUpdateService logFileModelUpdateService;

    @Autowired
    LogDisplayTypeModelGetManagerService logTypeModelGetManagerService;

    @Autowired
    RootLogModelService rootLogModelService;

    @Autowired
    LogModelDeleteService logModelDeleteService;

    @Autowired
    public LogModelRestController(LogModelCreateService globalServerCreateService,
                                  LogModelDeleteService globalServerDeleteService,
                                  LogModelGetService globalServerGetService,
                                  LogModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @DeleteMapping(value = "/{id}/descendants", produces = "application/hal+json")
    public ResponseEntity delete(@PathVariable("id") String id) throws Exception {
        LogModel t = logModelDeleteService.deleteLogModelAndDescendants(id);
        Resources<LogModel> resources = new EmptiableResources(genericClass, Collections.singletonList(t));
        resources.add(linkTo(methodOn(getClass()).delete(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
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
    public ResponseEntity theGetter(LogGetterRequest getterRequest,
                                    Pageable pageable,
                                    PagedResourcesAssembler assembler) {
        getterRequest.setPageable(pageable);
        Page<LogModel> page = logTypeModelGetManagerService.theGetter(getterRequest, getterRequest.getLogDisplayType());
        PagedResources<LogModel> resources = pageToResources(page, assembler);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/{id}/{log-type}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogType(@PathVariable("id") String id,
                                        @PathVariable("log-type") LogDisplayType logType,
                                        @RequestParam(value = "child-log-display-type", required = false, defaultValue = "DEFAULT") String childLogDisplayTypeString) {
        ALogDisplayType model = logTypeModelGetManagerService.findOne(id, logType);

        LogDisplayType childLogDisplayType = LogDisplayType.valueOf(childLogDisplayTypeString);
        if (childLogDisplayType != LogDisplayType.DEFAULT) {
            if (logType == LogDisplayType.FORUPDATE) {
                ForUpdateLogModel forUpdateLogModel = (ForUpdateLogModel) model;
                List childLogModels = forUpdateLogModel.getChildLogModels();
                forUpdateLogModel.setChildLogModels(logTypeModelGetManagerService.getAsLogDisplayType(childLogModels, childLogDisplayType));
            } else if (logType == LogDisplayType.PAGE) {
                PageLogModel pageLogModel = (PageLogModel) model;
                List childLogModels = pageLogModel.getChildLogModels();
                pageLogModel.setChildLogModels(logTypeModelGetManagerService.getAsLogDisplayType(childLogModels, childLogDisplayType));
            }
        }

        Resources resources = new EmptiableResources(ALogDisplayType.class, Collections.singletonList(model));
        resources.add(linkTo(methodOn(getClass()).getLogType(id, logType, childLogDisplayTypeString)).withSelfRel());
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
    public ResponseEntity getRoot(@PathVariable("log-type") LogDisplayType logType,
                                  @RequestParam(value = "child-log-display-type", required = false, defaultValue = "DEFAULT") String childLogDisplayTypeString) throws Exception {
        ALogDisplayType root = logTypeModelGetManagerService.getRoot(logType);

        LogDisplayType childLogDisplayType = LogDisplayType.valueOf(childLogDisplayTypeString);
        if (childLogDisplayType != LogDisplayType.DEFAULT) {
            if (logType == LogDisplayType.FORUPDATE) {
                ForUpdateLogModel forUpdateLogModel = (ForUpdateLogModel) root;
                List childLogModels = forUpdateLogModel.getChildLogModels();
                forUpdateLogModel.setChildLogModels(logTypeModelGetManagerService.getAsLogDisplayType(childLogModels, childLogDisplayType));
            } else if (logType == LogDisplayType.PAGE) {
                PageLogModel pageLogModel = (PageLogModel) root;
                List childLogModels = pageLogModel.getChildLogModels();
                pageLogModel.setChildLogModels(logTypeModelGetManagerService.getAsLogDisplayType(childLogModels, childLogDisplayType));
            }
        }

        Resources resources = new EmptiableResources(ALogDisplayType.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).getRoot(logType, childLogDisplayTypeString)).withSelfRel());
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
    public ResponseEntity findChildren(
            @PathVariable("id") String id,
            @PathVariable(value = "level", required = false) Integer level
    ) throws Exception {
        List<LogModel> models = logModelGetService.findChildren(id, level);

        Resources resources = new EmptiableResources(LogModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildren(id, level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/ancestry"}, produces = {"application/hal+json"})
    public ResponseEntity ancestry(@PathVariable("id") String id) throws Exception {
        List<LogModel> models = logModelGetService.getAncestryLogModels(id);

        Resources resources = new EmptiableResources(LogModel.class, models);
        resources.add(linkTo(methodOn(getClass()).ancestry(id)).withSelfRel());

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

    @PutMapping(value = "/{id}/parent/{parent-id}", produces = {"application/hal+json"})
    public ResponseEntity<?> assignNewParent(
            @PathVariable("id") String id,
            @PathVariable("parent-id") String parentID
    ) throws Exception {
        LogModel model = logFileModelUpdateService.assignNewParent(id, parentID);
        return hateosBuilder(model, methodOn(getClass()).assignNewParent(id, parentID));
    }

    private ResponseEntity<?> hateosBuilder(LogModel model, Object invocationValue) {
        Resources<LogModel> resources = new EmptiableResources(LogModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}