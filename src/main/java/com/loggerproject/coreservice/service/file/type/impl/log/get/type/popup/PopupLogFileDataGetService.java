package com.loggerproject.coreservice.service.file.type.impl.log.get.type.popup;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import com.loggerproject.coreservice.service.file.type.impl.log.get.type.ATypedLogFileDataGetService;
import org.springframework.stereotype.Service;

@Service
public class PopupLogFileDataGetService extends ATypedLogFileDataGetService {

    @Override
    public LogType getLogType() {
        return LogType.POPUP;
    }

    @Override
    public FileModel getByLogModelInternal(FileModel lf) {
//        PopupLogFileDataOverride override = getLogFileDataOverride(lf);

//        FileModel nfl = new FileModel();
//        nfl.setId(getID(override, lf));
//        nfl.setMetadata(getMetadata(override, lf));
//        nfl.setData(getData(override, (LogFileData) lf.getData()));

        return lf;
    }

    private PopupLogFileDataOverride getLogFileDataOverride(FileModel lf) {
        PopupLogFileDataOverride override;

        try {
            LogFileData l = (LogFileData) lf.getData();
            override = l.getLogTypeOverride().getPopup();
        } catch (NullPointerException e) {
            override = new PopupLogFileDataOverride();
        }

        return override;
    }
}
