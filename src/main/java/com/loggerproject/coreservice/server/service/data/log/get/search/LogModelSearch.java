package com.loggerproject.coreservice.server.service.data.log.get.search;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.LogTypeModelGetManagerService;
import com.loggerproject.coreservice.server.service.data.log.get.search.model.SearchRequest;
import com.loggerproject.coreservice.server.service.data.log.get.search.model.SearchResponse;
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
    LogTypeModelGetManagerService logTypeModelGetManagerService;

    @Autowired
    LogModelGetService logModelGetService;


    public SearchResponse findLogs(SearchRequest request) throws Exception {
        Set<String> ids = findIDsByKeyword(request.getKeyword());
        List<GlobalModel> logs = new ArrayList<>();

        if (request.getLogType() != null) {
            logs.addAll(logTypeModelGetManagerService.getByIDs(ids, request.getLogType()));
        } else {
            logs.addAll(logModelGetService.findByIds(ids));
        }

        SearchResponse response = new SearchResponse();
        response.setLogs(logs);
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
