package com.loggerproject.coreservice.endpoint.api.util;


import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.endpoint.api.extra.EmptiableResources;
import com.loggerproject.coreservice.endpoint.api.extra.GlobalResponse;
import com.loggerproject.coreservice.endpoint.api.image.extra.SingleSourceImageURL;
import com.loggerproject.coreservice.service.util.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/image-upload")
public class ImageUploadRestController {

    @Autowired
    ImageUploadService imageUploadService;

    @Value("${image.upload.tmpdir}")
    private String tmpDir;

    @PostMapping(value = {"/url"}, produces = {"application/hal+json"})
    public ResponseEntity<?> uploadImageSourceURL(@RequestBody SingleSourceImageURL request) throws Exception {
        ImageModel model = imageUploadService.uploadImage(request.getUrl());
        return hateosBuilder(model, methodOn(getClass()).uploadImageSourceURL(request));
    }

    @PostMapping(value = "/file", produces = {"application/hal+json"})
    public ResponseEntity<?> uploadImageSourceFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new Exception("file is empty");
        }

        File file = new File(tmpDir + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        ImageModel model = imageUploadService.uploadImage(file);

        file.delete();
        // passing null param bc: No converter found capable of converting
        // from type
        // [@org.springframework.web.bind.annotation.RequestParam org.springframework.web.multipart.MultipartFile]
        // to type
        // [java.lang.String]
        return hateosBuilder(model, methodOn(getClass()).uploadImageSourceFile(null));
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosBuilder(ImageModel model, Object invocationValue) {
        Resources<ImageModel> resources = new EmptiableResources(ImageModel.class, Collections.singletonList(model), linkTo(invocationValue).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        // TODO throw custom Exceptions catch them here and switch-case the HttpStatus and response and error code
        GlobalResponse response;
        response = new GlobalResponse();
        response.setCode(1);
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
