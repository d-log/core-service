package com.loggerproject.coreservice.global.server.endpoint.api;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.global.server.endpoint.api.model.GlobalResponse;
import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.global.server.service.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.global.server.service.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public abstract class GlobalModelController<T extends GlobalModel> {

    protected Class<T> genericType;

    protected GlobalServerCreateService<T> globalServerCreateService;
    protected GlobalServerDeleteService<T> globalServerDeleteService;
    protected GlobalServerGetService<T> globalServerGetService;
    protected GlobalServerUpdateService<T> globalServerUpdateService;

    @Autowired
    DirectoryModelRepository directoryModelRepository;

    public GlobalModelController(GlobalServerCreateService<T> globalServerCreateService,
                                 GlobalServerDeleteService<T> globalServerDeleteService,
                                 GlobalServerGetService<T> globalServerGetService,
                                 GlobalServerUpdateService<T> globalServerUpdateService) {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GlobalModelController.class);

        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }

    @PostMapping(produces="application/hal+json")
    public ResponseEntity<?> create(@RequestBody T model) throws Exception {
        T t = globalServerCreateService.create(model);
        Resources<T> resources = new EmptiableResources(genericType, Collections.singletonList(t));
        resources.add(linkTo(methodOn(getClass()).create(model)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }

    @DeleteMapping(value = "/{id}", produces="application/hal+json")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
        T t = globalServerDeleteService.delete(id);
        Resources<T> resources = new EmptiableResources(genericType, Collections.singletonList(t));
        resources.add(linkTo(methodOn(getClass()).delete(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @DeleteMapping(value = {"/all"}, produces = {"application/hal+json"})
    public ResponseEntity<?> deleteAll() throws Exception {
        DeleteAllResponse response = globalServerDeleteService.deleteAll();
        Resources<DeleteAllResponse> resources = new EmptiableResources(genericType, Collections.singletonList(response));
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/{id}", produces="application/hal+json")
    public ResponseEntity<?> get(@PathVariable("id") String id) throws Exception {
        T model = globalServerGetService.validateAndFindOne(id);
        Resources<T> resources = new EmptiableResources(genericType, Collections.singletonList(model));
        resources.add(linkTo(methodOn(getClass()).get(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    /**
     * pageable URL parameters `?page=1&size=2&sort=metadata.created,desc`
     * @param pageable - values customizable via URL parameters - otherwise default will be in place
     * @param assembler - autowired
     * @return
     */
    @GetMapping(value = "/all", produces="application/hal+json")
    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
        Page<T> page = globalServerGetService.findAll(pageable);
        PagedResources<T> resources = assembler.toResource(page);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
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
