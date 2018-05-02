package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.tile;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.ATypedLogDirectoryFileDataGetService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileLogDirectoryFileDataGetService extends ATypedLogDirectoryFileDataGetService {

    @Override
    public LogDirectoryType getLogType() {
        return LogDirectoryType.TILE;
    }

    @Override
    public FileModel getByLogDirectoryModelInternal(FileModel lf) {
        TileLogDirectoryFileDataOverride override = getLogDirectoryFileDataOverride(lf);

        FileModel nfl = new FileModel();
        nfl.setId(getID(override, lf));
        nfl.setMetadata(getMetadata(override, lf));
        nfl.setData(getData(override, (LogDirectoryFileData) lf.getData()));

        return nfl;
    }

    private TileLogDirectoryFileDataOverride getLogDirectoryFileDataOverride(FileModel lf) {
        TileLogDirectoryFileDataOverride override;

        try {
            LogDirectoryFileData l = (LogDirectoryFileData) lf.getData();
            override = l.getLogDirectoryTypeOverride().getTile();
        } catch (NullPointerException e) {
            override = new TileLogDirectoryFileDataOverride();
        }

        return override;
    }

    private TileLogDirectoryFileData getData(TileLogDirectoryFileDataOverride override, LogDirectoryFileData l) {
        TileLogDirectoryFileData tile = new TileLogDirectoryFileData();

        tile.setOrganization(l.getOrganization());
        tile.setLogDatas(getLogData(override, l.getLogDatas()));

        return tile;
    }

    private List<LogData> getLogData(TileLogDirectoryFileDataOverride override, List<LogData> logDatas) {
        List<LogData> newLogDatas = new ArrayList<>();

        Integer index = override.getLogDataToDisplayIndex();
        if (index != null) {
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
