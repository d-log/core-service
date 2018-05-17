package com.loggerproject.coreservice.endpoint.api.file.type.tag;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.endpoint.api.file.type.AFileModelRestController;
import com.loggerproject.coreservice.service.file.type.impl.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/file/tag")
public class TagFileModelRestController extends AFileModelRestController {

    @Autowired
    TagFileModelUpdateService tagFileModelUpdateService;

    @Autowired
    public TagFileModelRestController(TagFileModelCreateService globalServerCreateService,
                                      TagFileModelDeleteService globalServerDeleteService,
                                      TagFileModelGetService globalServerGetService,
                                      TagFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    //////////////
    // UPDATERS //
    //////////////

    @PutMapping(produces = {"application/hal+json"})
    public ResponseEntity<?> updateLog(@RequestBody FileModel model) throws Exception {
        FileModel modelUpdated = tagFileModelUpdateService.updateWholeModelAndSyncOtherDocuments(model);
        return hateosBuilder(modelUpdated, methodOn(getClass()).updateLog(model));
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosBuilder(FileModel model, Object invocationValue) {
        Resources<FileModel> resources = new EmptiableResources(FileModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}