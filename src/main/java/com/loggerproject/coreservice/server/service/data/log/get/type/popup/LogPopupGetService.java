package com.loggerproject.coreservice.server.service.data.log.get.type.popup;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import org.springframework.stereotype.Service;

@Service
public class LogPopupGetService extends ALogTypeGetService<LogPopupModel> {
    @Override
    public LogPopupModel getByLogModel(LogModel log) {
        LogPopupModel logPopup = getBaseLogPopupModel(log);
        setLogPopupModel(logPopup, log);
        return logPopup;
    }

    private LogPopupModel getBaseLogPopupModel(LogModel log) {
        LogPopupModel logPopup;

        try {
            logPopup = log.getLogTypes().getLogPopupModel();
        } catch (NullPointerException e) {
            logPopup = new LogPopupModel();
        }

        return logPopup;
    }

    private void setLogPopupModel(LogPopupModel logPopupModel, LogModel log) {
    }
}
