package com.loggerproject.coreservice.service.log.get.search;

import com.loggerproject.coreservice.data.model.directory.DirectoryModel;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.service.directory.DirectoryModelService;
import com.loggerproject.coreservice.service.tag.TagModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LogSearch {

    @Autowired
    TagModelService tagModelService;

    @Autowired
    DirectoryModelService directoryModelService;

    public Collection<String> findByKeyword(String keyword) {
        Set<String> logIDs = new HashSet<>();

        List<DirectoryModel> directoryModels = directoryModelService.findByName(keyword);
        for (DirectoryModel model : directoryModels) {
            logIDs.addAll(model.getLogIDs());
        }

        List<TagModel> tagModels = tagModelService.findByName(keyword);
        for (TagModel model : tagModels) {
            logIDs.addAll(model.getLogIDs());
        }

        return logIDs;
    }
}
