package com.loggerproject.coreservice.endpoint.api;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.endpoint.api.extra.GlobalResponse;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.aglobal.delete.AGlobalModelDeleteService;
import com.loggerproject.coreservice.service.aglobal.delete.model.DeleteAllResponse;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.aglobal.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
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
public abstract class AGlobalModelRestController<T extends GlobalModel> {

    protected Class genericClass;

    protected AGlobalModelCreateService<T> globalServerCreateService;
    protected AGlobalModelDeleteService<T> globalServerDeleteService;
    protected AGlobalModelGetService<T> globalServerGetService;
    protected AGlobalModelUpdateService<T> globalServerUpdateService;

    public AGlobalModelRestController(AGlobalModelCreateService globalServerCreateService,
                                      AGlobalModelDeleteService globalServerDeleteService,
                                      AGlobalModelGetService globalServerGetService,
                                      AGlobalModelUpdateService globalServerUpdateService) {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), AGlobalModelRestController.class);

        this.globalServerCreateService = globalServerCreateService;
        this.globalServerDeleteService = globalServerDeleteService;
        this.globalServerGetService = globalServerGetService;
        this.globalServerUpdateService = globalServerUpdateService;
    }

    @PostMapping(produces = "application/hal+json")
    public ResponseEntity create(@RequestBody T t) throws Exception {
        T nt = globalServerCreateService.create(t);
        Resources<T> resources = new EmptiableResources(genericClass, Collections.singletonList(nt));
        resources.add(linkTo(methodOn(getClass()).create(t)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }

    @DeleteMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity delete(@PathVariable("id") String id) throws Exception {
        T t = globalServerDeleteService.delete(id);
        Resources<T> resources = new EmptiableResources(genericClass, Collections.singletonList(t));
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
        T model = globalServerGetService.validateAndFindOne(id);
        Resources<T> resources = new EmptiableResources(genericClass, Collections.singletonList(model));
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
        Page<T> page = globalServerGetService.findAll(pageable);
        PagedResources<T> resources = pageToResources(page, assembler);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    protected PagedResources<T> pageToResources(Page page, PagedResourcesAssembler assembler) {
        PagedResources resources;

        if (page.getContent().size() == 0) {
            resources = assembler.toEmptyResource(page, genericClass, null);
        } else {
            resources = assembler.toResource(page);
        }

        return resources;
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
