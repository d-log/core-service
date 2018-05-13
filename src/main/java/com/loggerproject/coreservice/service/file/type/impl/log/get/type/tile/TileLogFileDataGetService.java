package com.loggerproject.coreservice.service.file.type.impl.log.get.type.tile;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import com.loggerproject.coreservice.service.file.type.impl.log.get.type.ATypedLogFileDataGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileLogFileDataGetService extends ATypedLogFileDataGetService {

    @Autowired
    TagFileModelGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileModelGetService logDirectoryFileDataGetService;

    @Override
    public LogType getLogType() {
        return LogType.TILE;
    }

    @Override
    public FileModel getByLogModelInternal(FileModel lf) {
        TileLogFileDataOverride override = getLogFileDataOverride(lf);

        FileModel nfl = new FileModel();
        nfl.setId(getID(override, lf));
        nfl.setMetadata(getMetadata(override, lf));
        nfl.setData(getData(override, (LogFileData) lf.getData()));

        return nfl;
    }

    private TileLogFileDataOverride getLogFileDataOverride(FileModel lf) {
        TileLogFileDataOverride override;

        try {
            LogFileData l = (LogFileData) lf.getData();
            override = l.getLogTypeOverride().getTile();
        } catch (NullPointerException e) {
            override = new TileLogFileDataOverride();
        }

        return override;
    }

    private TileLogFileData getData(TileLogFileDataOverride override, LogFileData l) {
        TileLogFileData tile = new TileLogFileData();

        tile.setOrganization(l.getOrganization());
        tile.setLogDatas(getLogData(override, l.getLogDatas()));
        tile.setParentLogDirectoryFileDatas(logDirectoryFileDataGetService.findByIds(l.getOrganization().getParentLogDirectoryFileIDs()));
        tile.setTagFileDatas(tagFileDataGetService.findByIds(l.getOrganization().getTagFileIDs()));
        tile.setNumLogDatas(l.getLogDatas().size());

        return tile;
    }

    private List<LogData> getLogData(TileLogFileDataOverride override, List<LogData> logDatas) {
        List<LogData> newLogDatas = new ArrayList<>();

        Integer index = override.getLogDataToDisplayIndex();
        if (index != null && (0 <= index && index < logDatas.size())) {
            newLogDatas.add(logDatas.get(index));
        } else {
            if (logDatas.size() == 1) {
                newLogDatas.add(logDatas.get(0));
            } else {
                // TODO find what log data to display
                newLogDatas.add(logDatas.get(0));
            }
        }

        return newLogDatas;
    }
}
