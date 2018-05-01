//package com.loggerproject.coreservice.server.service.file.delete;
//
//import com.loggerproject.coreservice.server.data.document.file.FileModel;
//import com.loggerproject.coreservice.server.service.file.create.FileModelCreateService;
//import com.loggerproject.coreservice.server.service.file.get.FileModelGetService;
//import com.loggerproject.coreservice.server.service.file.update.FileModelUpdateService;
//import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FileModelDeleteService extends AFileDataDeleteService<FileModel> {
//
//    @Autowired
//    public FileModelDeleteService(@Lazy FileModelCreateService globalServerCreateService,
//                                  @Lazy FileModelDeleteService globalServerDeleteService,
//                                  @Lazy FileModelGetService globalServerGetService,
//                                  @Lazy FileModelUpdateService globalServerUpdateService) {
//        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
//    }
//
//    public FileModel validateDelete(FileModel model) throws Exception {
//        return model;
//    }
//
//    @Override
//    public FileModel beforeDelete(FileModel model) throws Exception {
//        model = validateDelete(model);
//        return super.beforeDelete(model);
//    }
//}
