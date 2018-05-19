package com.loggerproject.coreservice.service.log.get.type.popup;

import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;

@Data
public class PopupLogModel extends ALogDisplayType {
    String id;

    public PopupLogModel() {
        this.setLogDisplayType(LogDisplayType.POPUP);
    }
}
