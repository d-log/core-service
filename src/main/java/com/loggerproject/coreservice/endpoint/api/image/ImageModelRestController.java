package com.loggerproject.coreservice.endpoint.api.image;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.endpoint.api.AGlobalModelRestController;
import com.loggerproject.coreservice.service.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.image.get.ImageGetterRequest;
import com.loggerproject.coreservice.service.image.get.ImageModelGetService;
import com.loggerproject.coreservice.service.image.update.ImageModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class ImageModelRestController extends AGlobalModelRestController<ImageModel> {

    @Autowired
    ImageModelGetService imageModelGetService;

    @Autowired
    public ImageModelRestController(ImageModelCreateService globalServerCreateService,
                                    ImageModelDeleteService globalServerDeleteService,
                                    ImageModelGetService globalServerGetService,
                                    ImageModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity theGetter(ImageGetterRequest getterRequest, Pageable pageable, PagedResourcesAssembler assembler) {
        getterRequest.setPageable(pageable);
        Page<ImageModel> page = imageModelGetService.theGetter(getterRequest);
        PagedResources<ImageModel> resources = pageToResources(page, assembler);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}