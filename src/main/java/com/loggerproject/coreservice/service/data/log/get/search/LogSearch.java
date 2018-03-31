package com.loggerproject.coreservice.service.data.log.get.search;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LogSearch {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    public Collection<String> findIDsByKeyword(String keyword) {
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
