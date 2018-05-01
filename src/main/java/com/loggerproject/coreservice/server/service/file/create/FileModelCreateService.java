//package com.loggerproject.coreservice.server.service.file.create;
//
//import com.loggerproject.coreservice.global.server.afiledata.create.AFileDataCreateService;
//import com.loggerproject.coreservice.server.data.document.file.FileModel;
//import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
//import com.loggerproject.coreservice.server.service.file.delete.FileModelDeleteService;
//import com.loggerproject.coreservice.server.service.file.get.FileModelGetService;
//import com.loggerproject.coreservice.server.service.file.update.FileModelUpdateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FileModelCreateService extends AFileDataCreateService<FileModel> {
//
//    @Autowired
//    FileModelGetService fileModelGetService;
//
//    @Autowired
//    public FileModelCreateService(FileModelRepository repository,
//                                  @Lazy FileModelCreateService globalServerCreateService,
//                                  @Lazy FileModelDeleteService globalServerDeleteService,
//                                  @Lazy FileModelGetService globalServerGetService,
//                                  @Lazy FileModelUpdateService globalServerUpdateService) {
//        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
//    }
//
//    @Override
//    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
//        return model;
//    }
//}
