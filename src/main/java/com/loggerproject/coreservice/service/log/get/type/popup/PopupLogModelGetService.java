package com.loggerproject.coreservice.service.log.get.type.popup;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import com.loggerproject.coreservice.service.log.get.type.ATypeLogModelGetService;
import org.springframework.stereotype.Service;

@Service
public class PopupLogModelGetService extends ATypeLogModelGetService {

    @Override
    public LogDisplayType getLogType() {
        return LogDisplayType.POPUP;
    }

    @Override
    public PopupLogModel getByLogModelInternal(LogModel model) {
        PopupLogModel alt = new PopupLogModel();

        alt.setId(model.getId());
        alt.setMetadata(model.getMetadata());
        alt.setLogOrganization(model.getLogOrganization());
        alt.setLogContents(model.getLogContents());

        return alt;
    }

    private PopupLogModelOverride getLogFileDataOverride(LogModel model) {
        PopupLogModelOverride override;

        try {
            override = model.getLogDisplayOverride().getPopup();
        } catch (NullPointerException e) {
            override = new PopupLogModelOverride();
        }

        return override;
    }
}
