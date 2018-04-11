package com.loggerproject.coreservice.server.service.data.log.get.type.tile;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import org.springframework.stereotype.Service;

@Service
public class LogTileGetService extends ALogTypeGetService<LogTileModel> {

    @Override
    public LogTileModel getByLogModel(LogModel log) {
        LogTileModel logTile = log.getLogTypes().getLogTileModel();

        if (logTile.getID() == null) {
            logTile.setID(log.getID());
        }

        if (logTile.getMetadata() == null) {
            logTile.setMetadata(log.getMetadata());
        }

        if (logTile.getTitle() == null) {
            logTile.setTitle(log.getTitle());
        }

        if (logTile.getDirectoryIDs() == null) {
            logTile.setDirectoryIDs(log.getDirectoryIDs());
        }

        if (logTile.getTagIDs() == null) {
            logTile.setTagIDs(log.getTagIDs());
        }

        setLogDataToDisplay(logTile, log);

        return logTile;
    }

    private void setLogDataToDisplay(LogTileModel logTile, LogModel log) {
        LogData logDataToDisplay;

        Integer index = logTile.getLogDataToDisplayIndex();
        if (index != null) {
            logDataToDisplay = log.getLogDatas().get(index);
        } else {
            if (log.getLogDatas().size() == 1) {
                logDataToDisplay = log.getLogDatas().get(0);
            } else {
                // TODO find what log data to display
                logDataToDisplay = log.getLogDatas().get(0);
            }
        }

        logTile.setLogDataToDisplay(logDataToDisplay);
    }
}
