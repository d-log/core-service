package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ATypedLogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageLogFileDataGetService extends ATypedLogFileDataGetService {

    @Autowired
    TagFileModelGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileModelGetService logDirectoryFileDataGetService;

    @Override
    public LogType getLogType() {
        return LogType.PAGE;
    }


    @Override
    public FileModel getByLogModelInternal(FileModel lf) {
        PageLogFileDataOverride override = getBaseLogPageModel(lf);

        FileModel nfl = new FileModel();
        nfl.setId(getID(override, lf));
        nfl.setMetadata(getMetadata(override, lf));
        nfl.setData(getData(override, (LogFileData) lf.getData()));

        return nfl;
    }

    private PageLogFileDataOverride getBaseLogPageModel(FileModel lf) {
        PageLogFileDataOverride override;

        try {
            LogFileData l = (LogFileData) lf.getData();
            override = l.getLogTypeOverride().getPage();
        } catch (NullPointerException e) {
            override = new PageLogFileDataOverride();
        }

        return override;
    }

    private PageLogFileData getData(PageLogFileDataOverride override, LogFileData l) {
        PageLogFileData page = new PageLogFileData();

        page.setOrganization(l.getOrganization());
        page.setLogDatas(l.getLogDatas());
        page.setParentLogDirectoryFileDatas(logDirectoryFileDataGetService.findByIds(l.getOrganization().getParentLogDirectoryFileIDs()));
        page.setTagFileDatas(tagFileDataGetService.findByIds(l.getOrganization().getTagFileIDs()));

        return page;
    }
}
