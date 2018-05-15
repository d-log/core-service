package com.loggerproject.coreservice.service.file.type.impl.log.get.type.forupdate;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import com.loggerproject.coreservice.service.file.type.impl.log.get.type.ATypedLogFileDataGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForUpdateLogFileDataGetService extends ATypedLogFileDataGetService {

    @Autowired
    TagFileModelGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileModelGetService logDirectoryFileDataGetService;

    @Override
    public LogType getLogType() {
        return LogType.FORUPDATE;
    }

    @Override
    public FileModel getByLogModelInternal(FileModel lf) {
        FileModel nfl = new FileModel();
        nfl.setId(lf.getId());
        nfl.setMetadata(lf.getMetadata());
        nfl.setData(getData((LogFileData) lf.getData()));

        return nfl;
    }

    private ForUpdateLogFileData getData(LogFileData l) {
        ForUpdateLogFileData tile = new ForUpdateLogFileData();

        tile.setOrganization(l.getOrganization());
        tile.setLogDatas(l.getLogDatas());
        tile.setLogTypeOverride(l.getLogTypeOverride());
        tile.setParentLogDirectoryFileDatas(logDirectoryFileDataGetService.findByIds(l.getOrganization().getParentLogDirectoryFileIDs()));
        tile.setTagFileDatas(tagFileDataGetService.findByIds(l.getOrganization().getTagFileIDs()));
        tile.setNumLogDatas(l.getLogDatas().size());

        return tile;
    }
}
