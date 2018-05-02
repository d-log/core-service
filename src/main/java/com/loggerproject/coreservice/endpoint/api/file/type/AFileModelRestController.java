package com.loggerproject.coreservice.endpoint.api.file.type;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.endpoint.api.extra.GlobalResponse;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.afiledata.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@ResponseBody
@SuppressWarnings("unchecked")
public abstract class AFileModelRestController {

    protected Class genericClass;

    protected AFileModelCreateService globalServerCreateService;
    protected AFileModelDeleteService globalServerDeleteService;
    protected AFileModelGetService globalServerGetService;
    protected AFileModelUpdateService globalServerUpdateService;

    public AFileModelRestController(AFileModelCreateService globalServerCreateService,
                                    AFileModelDeleteService globalServerDeleteService,
                                    AFileModelGetService globalServerGetService,
                                    AFileModelUpdateService globalServerUpdateService) {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AFileModelRestController.class);

        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }

    @PostMapping(produces = "application/hal+json")
    public ResponseEntity create(@RequestBody FileModel model) throws Exception {
        FileModel t = globalServerCreateService.create(model);
        Resources<FileModel> resources = new EmptiableResources(genericClass, Collections.singletonList(t));
        resources.add(linkTo(methodOn(getClass()).create(model)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }

    @DeleteMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity delete(@PathVariable("id") String id) throws Exception {
        FileModel t = globalServerDeleteService.delete(id);
        Resources<FileModel> resources = new EmptiableResources(genericClass, Collections.singletonList(t));
        resources.add(linkTo(methodOn(getClass()).delete(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @DeleteMapping(value = {"/all"}, produces = {"application/hal+json"})
    public ResponseEntity deleteAll() throws Exception {
        DeleteAllResponse response = globalServerDeleteService.deleteAll();
        Resources<DeleteAllResponse> resources = new EmptiableResources(genericClass, Collections.singletonList(response));
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity get(@PathVariable("id") String id) throws Exception {
        FileModel model = globalServerGetService.validateAndFindOne(id);
        Resources<FileModel> resources = new EmptiableResources(genericClass, Collections.singletonList(model));
        resources.add(linkTo(methodOn(getClass()).get(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    /**
     * pageable URL parameters `?page=1&size=2&sort=metadata.created,desc`
     *
     * @param pageable  - values customizable via URL parameters - otherwise default will be in place
     * @param assembler - autowired
     * @return
     */
    @GetMapping(value = "/all", produces = "application/hal+json")
    public ResponseEntity getAll(Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
        Page<FileModel> page = globalServerGetService.findAll(pageable);
        PagedResources<FileModel> resources = assembler.toResource(page);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        // TODO return 4XX and/or 5XX HTTP status based on exception
        if (e instanceof ModelNotFoundException) {
            GlobalResponse response = new GlobalResponse();
            response.setCode(1);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            GlobalResponse response = new GlobalResponse();
            response.setCode(1);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
