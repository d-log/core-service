package com.loggerproject.coreservice.endpoint.api.directory;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.EmptiableResources;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/directory")
public class DirectoryModelRestController extends GlobalModelController<DirectoryModel> {

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    public DirectoryModelRestController(DirectoryModelCreateService globalServerCreateService,
                                        DirectoryModelDeleteService globalServerDeleteService,
                                        DirectoryModelGetService globalServerGetService,
                                        DirectoryModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @GetMapping(value = {"/{id}/children/{level}", "/{id}/children"}, produces = {"application/hal+json"})
    public ResponseEntity<?> findChildren(@PathVariable("id") String id, @PathVariable(value = "level", required = false) Integer level) throws Exception {
        List<DirectoryModel> models = directoryModelGetService.findChildren(id, level == null ? 1 : level);

        Resources<DirectoryModel> resources = new EmptiableResources(DirectoryModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildren(id, level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }

    @GetMapping(value = {"/{id}/parent"}, produces = {"application/hal+json"})
    public ResponseEntity<?> findParent(@PathVariable("id") String id) throws Exception {
        List<DirectoryModel> models = directoryModelGetService.findParents(id);

        Resources<DirectoryModel> resources = new EmptiableResources(DirectoryModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findParent(id)).withSelfRel());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}
