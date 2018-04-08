package com.loggerproject.coreservice.server.endpoint.api.customlogdata;

import com.loggerproject.coreservice.global.server.endpoint.api.EmptiableResources;
import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.data.document.customlogdata.extra.ValidateDataStatement;
import com.loggerproject.coreservice.server.service.data.customlogdata.create.CustomLogDataModelCreateService;
import com.loggerproject.coreservice.server.service.data.customlogdata.delete.CustomLogDataModelDeleteService;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import com.loggerproject.coreservice.server.service.data.customlogdata.update.CustomLogDataModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/custom-log-data")
public class CustomLogDataModelRestController extends GlobalModelController<CustomLogDataModel> {

    @Autowired
    CustomLogDataModelUpdateService customLogDataModelUpdateService;

    @Autowired
    public CustomLogDataModelRestController(CustomLogDataModelCreateService globalServerCreateService,
                                            CustomLogDataModelDeleteService globalServerDeleteService,
                                            CustomLogDataModelGetService globalServerGetService,
                                            CustomLogDataModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @PutMapping(value = {"/{custom-log-data-id}/data-schema-json"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateDataSchemaJSON(@PathVariable("custom-log-data-id") String id, @RequestBody String json) throws Exception {
        CustomLogDataModel modelUpdated = customLogDataModelUpdateService.updateDataSchemaJSON(id, json);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateDataSchemaJSON(id, json)).withSelfRel());
    }

    @PutMapping(value = {"/{custom-log-data-id}/validate-data-statement"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateValidateDataStatement(@PathVariable("custom-log-data-id") String id, @RequestBody ValidateDataStatement validateDataStatement) throws Exception {
        CustomLogDataModel modelUpdated = customLogDataModelUpdateService.updateValidateDataStatement(id, validateDataStatement);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateValidateDataStatement(id, validateDataStatement)).withSelfRel());
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosResponseEntityBuilder(CustomLogDataModel model, Link link) {
        Resources<CustomLogDataModel> resources = new EmptiableResources(CustomLogDataModel.class, Collections.singletonList(model), link);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}
