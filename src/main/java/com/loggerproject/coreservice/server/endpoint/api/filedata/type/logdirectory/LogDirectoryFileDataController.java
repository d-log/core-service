package com.loggerproject.coreservice.server.endpoint.api.filedata.type.logdirectory;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.endpoint.api.filedata.a.AFileDataController;
import com.loggerproject.coreservice.server.endpoint.api.filedata.a.extra.EmptiableResources;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.RootLogDirectoryService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
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
@RequestMapping("/api/file-data/log-directory")
public class LogDirectoryFileDataController extends AFileDataController {

    @Autowired
    RootLogDirectoryService rootLogDirectoryService;

    @Autowired
    LogDirectoryFileDataGetService logDirectoryFileDataGetService;

    @Autowired
    public LogDirectoryFileDataController(LogDirectoryFileDataCreateService globalServerCreateService,
                                          LogDirectoryFileDataDeleteService globalServerDeleteService,
                                          LogDirectoryFileDataGetService globalServerGetService,
                                          LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @PostMapping(value = "/root", produces = {"application/hal+json"})
    public ResponseEntity createRoot() throws Exception {
        FileModel root = rootLogDirectoryService.createRoot();

        Resources resources = new EmptiableResources(FileModel.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).createRoot()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/root", produces = {"application/hal+json"})
    public ResponseEntity getRoot() throws Exception {
        FileModel root = logDirectoryFileDataGetService.getRoot();

        Resources resources = new EmptiableResources(FileModel.class, Collections.singletonList(root));
        resources.add(linkTo(methodOn(getClass()).getRoot()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/root/children/{level}", "/root/children"}, produces = {"application/hal+json"})
    public ResponseEntity findChildrenOfRoot(@PathVariable(value = "level", required = false) Integer level) throws Exception {
        FileModel model = logDirectoryFileDataGetService.getRoot();
        List<FileModel> models = logDirectoryFileDataGetService.findChildren(model.getId(), level);

        Resources resources = new EmptiableResources(FileModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildrenOfRoot(level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/children/{level}", "/{id}/children"}, produces = {"application/hal+json"})
    public ResponseEntity findChildren(@PathVariable("id") String id, @PathVariable(value = "level", required = false) Integer level) throws Exception {
        List<FileModel> models = logDirectoryFileDataGetService.findChildren(id, level);

        Resources resources = new EmptiableResources(FileModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findChildren(id, level)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = {"/{id}/parent"}, produces = {"application/hal+json"})
    public ResponseEntity findParent(@PathVariable("id") String id) throws Exception {
        List<FileModel> models = logDirectoryFileDataGetService.findParents(id);

        Resources resources = new EmptiableResources(FileModel.class, models);
        resources.add(linkTo(methodOn(getClass()).findParent(id)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}