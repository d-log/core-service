package com.loggerproject.coreservice.server.service.data.log.get.type.tile;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogTileGetService extends ALogTypeGetService<LogTileModel> {

    @Override
    public LogTileModel getByLogModelInternal(LogModel log) {
        LogTileModel logTile = getBaseLogTileModel(log);
        setLogTileModel(logTile, log);
        return logTile;
    }

    private LogTileModel getBaseLogTileModel(LogModel log) {
        LogTileModel logTile;

        try {
            logTile = log.getLogTypes().getLogTileModel();
        } catch (NullPointerException e) {
            logTile = new LogTileModel();
        }

        return logTile;
    }

    private void setLogTileModel(LogTileModel logTile, LogModel log) {
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

        setLogDatasToDisplay(logTile, log);
    }

    private void setLogDatasToDisplay(LogTileModel logTile, LogModel log) {
        List<LogData> logDatas = new ArrayList<>();

        Integer index = logTile.getLogDataToDisplayIndex();
        if (index != null) {
            logDatas.add(log.getLogDatas().get(index));
        } else {
            if (log.getLogDatas().size() == 1) {
                logDatas.add(log.getLogDatas().get(0));
            } else {
                // TODO find what log data to display
                logDatas.add(log.getLogDatas().get(0));
            }
        }

        logTile.setLogDatas(logDatas);
    }
}
