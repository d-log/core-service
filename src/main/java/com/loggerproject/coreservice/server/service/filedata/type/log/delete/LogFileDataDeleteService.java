package com.loggerproject.coreservice.server.service.filedata.type.log.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.LogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogFileDataDeleteService extends AFileDataDeleteService<LogFileData> {

    @Autowired
    LogDirectoryFileDataGetService directoryGetService;

    @Autowired
    LogDirectoryFileDataUpdateService directoryUpdateService;

    @Autowired
    TagFileDataGetService tagGetService;

    @Autowired
    TagFileDataUpdateService tagUpdateService;

    @Autowired
    public LogFileDataDeleteService(@Lazy LogFileDataCreateService globalServerCreateService,
                                    @Lazy LogFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogFileDataGetService globalServerGetService,
                                    @Lazy LogFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) throws Exception {
        return model;
    }

    /**
     * TODO should do exception recovery when exception thrown (undo all updates)
     *
     * @param lf
     * @return
     * @throws Exception
     */
    @Override
    public FileModel afterDeleteUpdateOtherDocuments(FileModel lf) throws Exception {
        LogFileData l = (LogFileData) lf.getData();
        for (String dID : l.getOrganization().getParentLogDirectoryFileIDs()) {
            FileModel df = directoryGetService.findOne(dID);
            if (df != null) {
                LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
                d.getLogFileIDs().remove(lf.getId());
                directoryUpdateService.update(df);
            }
        }

        for (String tID : l.getOrganization().getTagFileIDs()) {
            FileModel tf = tagGetService.findOne(tID);
            if (tf != null) {
                TagFileData t = (TagFileData) tf.getData();
                t.getLogFileIDs().remove(lf.getId());
                tagUpdateService.update(tf);
            }
        }

        return lf;
    }
}
