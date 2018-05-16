package com.loggerproject.coreservice.endpoint.api.file;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.service.file.FileModelGetService;
import com.loggerproject.coreservice.service.file.extra.FileGetterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
@SuppressWarnings(value = "unchecked")
public class FileModelRestController {

    @Autowired
    FileModelGetService fileModelGetService;

    @GetMapping(value = "/the-getter", produces = {"application/hal+json"})
    public ResponseEntity<?> theGetter(FileGetterRequest getterRequest,
                                       Pageable pageable,
                                       PagedResourcesAssembler assembler) {
        getterRequest.setPageable(pageable);
        Page<FileModel> page = fileModelGetService.theGetter(getterRequest);

        Resources resources;
        if (page.getContent().size() == 0) {
            resources = assembler.toEmptyResource(page, FileModel.class, null);
        } else {
            resources = assembler.toResource(page);
        }

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}
