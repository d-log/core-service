package com.loggerproject.coreservice.server.endpoint.api.file;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.file.FileModelGetService;
import com.loggerproject.coreservice.server.service.file.extra.FileGetterRequest;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-model")
@SuppressWarnings(value = "unchecked")
public class FileModelRestController {

    @Autowired
    FileModelGetService fileModelGetService;

    @GetMapping(value = "/the-getter", produces = {"application/hal+json"})
    public ResponseEntity<?> theGetter(@RequestParam(value = "millisecond-threshold", required = false) Long millisecondThreshold,
                                       @RequestParam(value = "search", required = false) String search,
                                       @RequestParam(value = "tag-id", required = false) String tagID,
                                       @RequestParam(value = "directory-id", required = false) String directoryID,
                                       @RequestParam(value = "file-type", required = false) String fileType,
                                       @RequestParam(value = "log-type", required = false) LogType logType,
                                       Pageable pageable,
                                       PagedResourcesAssembler assembler) {
        FileGetterRequest getterRequest = new FileGetterRequest();
        getterRequest.setFileType(fileType);
        getterRequest.setLogType(logType);
        getterRequest.setTagID(tagID);
        getterRequest.setDirectoryID(directoryID);
        getterRequest.setSearchString(search);
        getterRequest.setMillisecondThreshold(millisecondThreshold);
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
