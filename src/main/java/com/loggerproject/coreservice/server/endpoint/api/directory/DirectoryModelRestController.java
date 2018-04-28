package com.loggerproject.coreservice.server.endpoint.api.directory;

import com.loggerproject.coreservice.global.server.endpoint.api.EmptiableResources;
import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.service.data.directory.DirectoryRootService;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
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
@RequestMapping("/api/directory")
public class DirectoryModelRestController extends GlobalModelController<DirectoryModel> {

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    DirectoryRootService directoryRootService;

    @Autowired
    public DirectoryModelRestController(DirectoryModelCreateService globalServerCreateService,
                                        DirectoryModelDeleteService globalServerDeleteService,
                                        DirectoryModelGetService globalServerGetService,
                                        DirectoryModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @PostMapping(value = "/root", produces = {"application/hal+json"})
    public ResponseEntity createRoot() throws Exception {
        DirectoryModel root = directoryRootService.createRoot();

        Resources resources = new EmptiableResources(DirectoryModel.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).createRoot()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/root", produces = {"application/hal+json"})
    public ResponseEntity getRoot() {
        DirectoryModel root = directoryModelGetService.getRoot();

        Resources resources = new EmptiableResources(DirectoryModel.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).getRoot()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/root/children/{level}", "/root/children"}, produces = {"application/hal+json"})
    public ResponseEntity findChildrenOfRoot(@PathVariable(value = "level", required = false) Integer level) throws Exception {
        DirectoryModel model = directoryModelGetService.getRoot();
        List<DirectoryModel> models = directoryModelGetService.findChildren(model.getID(), level);

        Resources resources = new EmptiableResources(DirectoryModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildrenOfRoot(level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/children/{level}", "/{id}/children"}, produces = {"application/hal+json"})
    public ResponseEntity findChildren(@PathVariable("id") String id, @PathVariable(value = "level", required = false) Integer level) throws Exception {
        List<DirectoryModel> models = directoryModelGetService.findChildren(id, level);

        Resources resources = new EmptiableResources(DirectoryModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildren(id, level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/parent"}, produces = {"application/hal+json"})
    public ResponseEntity findParent(@PathVariable("id") String id) throws Exception {
        List<DirectoryModel> models = directoryModelGetService.findParents(id);

        Resources resources = new EmptiableResources(DirectoryModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findParent(id)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}
