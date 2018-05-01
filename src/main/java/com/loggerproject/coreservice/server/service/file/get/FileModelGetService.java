//package com.loggerproject.coreservice.server.service.file.get;
//
//import com.loggerproject.coreservice.global.server.afiledata.get.AFileDataGetService;
//import com.loggerproject.coreservice.server.data.document.file.FileModel;
//import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
//import com.loggerproject.coreservice.server.service.file.create.FileModelCreateService;
//import com.loggerproject.coreservice.server.service.file.delete.FileModelDeleteService;
//import com.loggerproject.coreservice.server.service.file.update.FileModelUpdateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FileModelGetService extends AFileDataGetService<FileModel> {
//
//    @Autowired
//    FileModelRepository fileModelRepository;
//
//    @Autowired
//    public FileModelGetService(FileModelRepository repository,
//                               @Lazy FileModelCreateService globalServerCreateService,
//                               @Lazy FileModelDeleteService globalServerDeleteService,
//                               @Lazy FileModelGetService globalServerGetService,
//                               @Lazy FileModelUpdateService globalServerUpdateService,
//                               @Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
//        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService, maxPageSize);
//    }
//}
