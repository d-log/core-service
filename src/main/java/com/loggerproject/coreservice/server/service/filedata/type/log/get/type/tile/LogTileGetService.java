package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeGetService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogTileGetService extends ALogTypeGetService<LogTileModel> {

    @Override
    public LogTileModel getByLogModelInternal(FileModel log) {
        LogTileModel logTile = getBaseLogTileModel(log);
        setLogTileModel(logTile, log);
        return logTile;
    }

    private LogTileModel getBaseLogTileModel(FileModel lf) {
        LogTileModel logTile;

        try {
            LogFileData l = (LogFileData) lf.getData();
            logTile = l.getLogTypeOverride().getTile();
        } catch (NullPointerException e) {
            logTile = new LogTileModel();
        }

        return logTile;
    }

    private void setLogTileModel(LogTileModel logTile, FileModel lf) {
        if (logTile.getID() == null) {
            logTile.setID(lf.getId());
        }

        if (logTile.getMetadata() == null) {
            logTile.setMetadata(lf.getMetadata());
        }

        LogFileData l = (LogFileData) lf.getData();
        if (logTile.getOrganization() == null) {
            logTile.setOrganization(l.getOrganization());
        }

        setLogDatasToDisplay(logTile, l);
    }

    private void setLogDatasToDisplay(LogTileModel logTile, LogFileData l) {
        List<LogData> logDatas = new ArrayList<>();

        Integer index = logTile.getLogDataToDisplayIndex();
        if (index != null) {
            logDatas.add(l.getLogDatas().get(index));
        } else {
            if (l.getLogDatas().size() == 1) {
                logDatas.add(l.getLogDatas().get(0));
            } else {
                // TODO find what log data to display
                logDatas.add(l.getLogDatas().get(0));
            }
        }

        logTile.setLogDatas(logDatas);
    }
}
