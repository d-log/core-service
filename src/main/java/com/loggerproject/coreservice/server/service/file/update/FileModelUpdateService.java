//package com.loggerproject.coreservice.server.service.file.update;
//
//import com.loggerproject.coreservice.global.server.afiledata.update.AFileDataUpdateService;
//import com.loggerproject.coreservice.server.data.document.file.FileModel;
//import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
//import com.loggerproject.coreservice.server.service.file.create.FileModelCreateService;
//import com.loggerproject.coreservice.server.service.file.delete.FileModelDeleteService;
//import com.loggerproject.coreservice.server.service.file.get.FileModelGetService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FileModelUpdateService extends AFileDataUpdateService<FileModel> {
//
//    @Autowired
//    FileModelGetService fileModelGetService;
//
//    @Autowired
//    public FileModelUpdateService(FileModelRepository repository,
//                                  @Lazy FileModelCreateService globalServerCreateService,
//                                  @Lazy FileModelDeleteService globalServerDeleteService,
//                                  @Lazy FileModelGetService globalServerGetService,
//                                  @Lazy FileModelUpdateService globalServerUpdateService) {
//        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
//    }
//}
