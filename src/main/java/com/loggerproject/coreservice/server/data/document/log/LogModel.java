package com.loggerproject.coreservice.server.data.document.log;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.log.model.LogType;
import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogData;
import com.loggerproject.coreservice.server.data.document.log.model.view.PageView;
import com.loggerproject.coreservice.server.data.document.log.model.view.PopupView;
import com.loggerproject.coreservice.server.data.document.log.model.view.TileView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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

    ALogData logData;

    // Optional
    Set<String> tagIDs;
    TileView tileView;
    PageView pageView;
    PopupView popupView;

//    @JsonSetter("logDatas")
//    @SuppressWarnings(value = "unchecked")
//    public void jsonSetLogDatas(JsonNode data) {
//        if (data.isArray()) {
//            logDatas = new ArrayList<>();
//
//            for (final JsonNode node : data) {
//                String logDataClass = node.get("logDataClass").textValue();
//
//                try {
//                    Class c = Class.forName("com.loggerproject.coreservice.server.data.document.log.model.logdata.impl." + logDataClass);
//                    Object obj = new Gson().fromJson(node.toString(), c);
//                    logDatas.add((ALogData)obj);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
    @JsonGetter("logDatas")
    public List jsonGetLogDats()  {
        return new ArrayList<>();
    }

//    @JsonGetter("logData")
//    public ALogData dldd()  {
//        return null;
//    }
}
