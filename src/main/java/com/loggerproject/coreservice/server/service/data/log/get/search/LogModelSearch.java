package com.loggerproject.coreservice.server.service.data.log.get.search;

import com.loggerproject.coreservice.global.server.service.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.search.model.SearchRequest;
import com.loggerproject.coreservice.server.service.data.log.get.search.model.SearchResponse;
import com.loggerproject.coreservice.server.service.data.log.get.type.detail.LogDetailModelService;
import com.loggerproject.coreservice.server.service.data.log.get.type.detail.LogDetailModel;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LogModelSearch {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    LogDetailModelService logDetailModelService;

    public SearchResponse findLogs(SearchRequest request) throws Exception {
        Set<String> logIDs = findIDsByKeyword(request.getKeyword());
        List<LogDetailModel> logDetailModels = new ArrayList<>();
        for (String logID : logIDs) {
            try {
                LogDetailModel logDetailModel = logDetailModelService.getByID(logID);
                logDetailModels.add(logDetailModel);
            }
            catch (ModelNotFoundException e) {
                // continue
            }
        }

        SearchResponse response = new SearchResponse();
        response.setLogDetails(logDetailModels);
        return response;
    }

    public Set<String> findIDsByKeyword(String keyword) {
        Set<String> logIDs = new HashSet<>();

        List<DirectoryModel> directoryModels = directoryModelGetService.findByName(keyword);
        for (DirectoryModel model : directoryModels) {
            logIDs.addAll(model.getLogIDs());
        }

        List<TagModel> tagModels = tagModelGetService.findByName(keyword);
        for (TagModel model : tagModels) {
            logIDs.addAll(model.getLogIDs());
        }

        return logIDs;
    }
}
