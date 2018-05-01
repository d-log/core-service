package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.popup;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeGetService;
import org.springframework.stereotype.Service;

@Service
public class LogPopupGetService extends ALogTypeGetService<LogPopupModel> {
    @Override
    public LogPopupModel getByLogModelInternal(FileModel lf) {
        LogPopupModel logPopup = getBaseLogPopupModel(lf);
        setLogPopupModel(logPopup, lf);
        return logPopup;
    }

    private LogPopupModel getBaseLogPopupModel(FileModel lf) {
        LogPopupModel logPopup;

        try {
            LogFileData l = (LogFileData) lf.getData();
            logPopup = l.getLogTypeOverride().getPopup();
        } catch (NullPointerException e) {
            logPopup = new LogPopupModel();
        }

        return logPopup;
    }

    private void setLogPopupModel(LogPopupModel logPopupModel, FileModel lf) {
    }
}
