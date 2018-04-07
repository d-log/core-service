package com.loggerproject.coreservice.data.document.log;

import com.loggerproject.coreservice.data.document.log.model.LogType;
import com.loggerproject.coreservice.data.document.log.model.logdata.ALogData;
import com.loggerproject.coreservice.data.document.log.model.view.PageView;
import com.loggerproject.coreservice.data.document.log.model.view.PopupView;
import com.loggerproject.coreservice.data.document.log.model.view.TileView;
import com.loggerproject.microserviceglobalresource.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(collection = "log")
public class LogModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;

    // required
    LogType logType;
    List<ALogData> logDatas; // size must be >= 1
    Set<String> directoryIDs; // size must be >= 1

    // Optional
    Set<String> tagIDs;
    TileView tileView;
    PageView pageView;
    PopupView popupView;

}
