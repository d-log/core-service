package com.loggerproject.coreservice.service.file.type.impl.log.delete;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.log.create.LogFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.log.get.regular.LogFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.log.update.LogFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.update.LogDirectoryFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogFileModelDeleteService extends AFileModelDeleteService<LogFileData> {

    @Autowired
    LogDirectoryFileModelGetService directoryGetService;

    @Autowired
    LogDirectoryFileModelUpdateService directoryUpdateService;

    @Autowired
    TagFileModelGetService tagGetService;

    @Autowired
    TagFileModelUpdateService tagUpdateService;

    @Autowired
    public LogFileModelDeleteService(@Lazy LogFileModelCreateService globalServerCreateService,
                                     @Lazy LogFileModelDeleteService globalServerDeleteService,
                                     @Lazy LogFileModelGetService globalServerGetService,
                                     @Lazy LogFileModelUpdateService globalServerUpdateService) {
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
