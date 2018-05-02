package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.page;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.ATypedLogDirectoryFileDataGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageLogDirectoryFileDataGetService extends ATypedLogDirectoryFileDataGetService {

    @Autowired
    TagFileModelGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileModelGetService logDirectoryFileDataGetService;

    @Override
    public LogDirectoryType getLogType() {
        return LogDirectoryType.PAGE;
    }


    @Override
    public FileModel getByLogDirectoryModelInternal(FileModel lf) {
        PageLogDirectoryFileDataOverride override = getBaseLogPageModel(lf);

        FileModel nfl = new FileModel();
        nfl.setId(getID(override, lf));
        nfl.setMetadata(getMetadata(override, lf));
        nfl.setData(getData(override, (LogDirectoryFileData) lf.getData()));

        return nfl;
    }

    private PageLogDirectoryFileDataOverride getBaseLogPageModel(FileModel lf) {
        PageLogDirectoryFileDataOverride override;

        try {
            LogDirectoryFileData l = (LogDirectoryFileData) lf.getData();
            override = l.getLogDirectoryTypeOverride().getPage();
        } catch (NullPointerException e) {
            override = new PageLogDirectoryFileDataOverride();
        }

        return override;
    }

    private PageLogDirectoryFileData getData(PageLogDirectoryFileDataOverride override, LogDirectoryFileData l) {
        PageLogDirectoryFileData page = new PageLogDirectoryFileData();

        page.setOrganization(l.getOrganization());
        page.setLogDatas(l.getLogDatas());
        page.setParentLogDirectoryFileDatas(logDirectoryFileDataGetService.findByIds(l.getOrganization().getParentLogDirectoryFileIDs()));
        page.setTagFileDatas(tagFileDataGetService.findByIds(l.getOrganization().getTagFileIDs()));

        return page;
    }
}
