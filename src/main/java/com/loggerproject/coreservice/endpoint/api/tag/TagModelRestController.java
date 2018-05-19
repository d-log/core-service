package com.loggerproject.coreservice.endpoint.api.tag;

import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.endpoint.api.AGlobalModelRestController;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.service.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
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
@RequestMapping("/api/tag")
public class TagModelRestController extends AGlobalModelRestController<TagModel> {

    @Autowired
    TagModelUpdateService tagFileModelUpdateService;

    @Autowired
    public TagModelRestController(TagModelCreateService globalServerCreateService,
                                  TagModelDeleteService globalServerDeleteService,
                                  TagModelGetService globalServerGetService,
                                  TagModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    //////////////
    // UPDATERS //«
    //////////////

    @PutMapping(produces = {"application/hal+json"})
    public ResponseEntity<?> updateLog(@RequestBody TagModel model) throws Exception {
        TagModel modelUpdated = tagFileModelUpdateService.updateWholeModelAndSyncOtherDocuments(model);
        return hateosBuilder(modelUpdated, methodOn(getClass()).updateLog(model));
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosBuilder(TagModel model, Object invocationValue) {
        Resources<TagModel> resources = new EmptiableResources(TagModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}