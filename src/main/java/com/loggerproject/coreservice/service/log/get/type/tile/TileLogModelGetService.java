package com.loggerproject.coreservice.service.log.get.type.tile;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.log.content.LogContent;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.get.type.ATypeLogModelGetService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileLogModelGetService extends ATypeLogModelGetService {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    LogModelGetService logModelGetService;

    @Override
    public LogDisplayType getLogType() {
        return LogDisplayType.TILE;
    }

    @Override
    public TileLogModel getByLogModelInternal(LogModel model) {
        TileLogModelOverride override = getLogFileDataOverride(model);

        TileLogModel alt = new TileLogModel();
        alt.setId(getID(override, model));
        alt.setMetadata(getMetadata(override, model));
        alt.setMetadata(model.getMetadata());
        alt.setLogOrganization(model.getLogOrganization());
        alt.setLogContents(getLogContentToDisplay(override, model.getLogContents()));
        alt.setParentLogModels(logModelGetService.findByIds(model.getLogOrganization().getParentLogIDs()));
        alt.setChildLogModels(logModelGetService.findByIds(model.getLogOrganization().getChildLogIDs()));
        alt.setTagModels(tagModelGetService.findByIds(model.getLogOrganization().getTagIDs()));

        return alt;
    }

    private TileLogModelOverride getLogFileDataOverride(LogModel model) {
        TileLogModelOverride override;

        try {
            override = model.getLogDisplayOverride().getTile();
        } catch (NullPointerException e) {
            override = new TileLogModelOverride();
        }

        return override;
    }

    private List<LogContent> getLogContentToDisplay(TileLogModelOverride override, List<LogContent> logContents) {
        List<LogContent> newLogDatas = new ArrayList<>();

        Integer index = override.getLogContentToDisplayIndex();
        if (index != null && (0 <= index && index < logContents.size())) {
            newLogDatas.add(logContents.get(index));
        } else {
            if (logContents.size() == 1) {
                newLogDatas.add(logContents.get(0));
            } else {
                // TODO find what log data to display
                newLogDatas.add(logContents.get(0));
            }
        }

        return newLogDatas;
    }
}
