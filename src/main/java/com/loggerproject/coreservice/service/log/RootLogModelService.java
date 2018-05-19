package com.loggerproject.coreservice.service.log;

import com.loggerproject.coreservice.data.model._shared.Metadata;
import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.log.organization.LogOrganization;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class RootLogModelService {

    public static final String ROOT_NAME = "ROOT_LOG";

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    LogModelRepository repository;

    public LogModel createRoot() throws Exception {
        List<LogModel> list = logModelGetService.findByName(ROOT_NAME);
        // if anything is to be changed here then will need to manually update it
        if (list.isEmpty()) {
            LogModel root = new LogModel();

            Metadata metadata = new Metadata();
            Date now = new Date();
            metadata.setName(ROOT_NAME);
            metadata.setDescription("This is the Root LogModel");
            metadata.setCreated(now);
            metadata.setLastUpdated(now);
            root.setMetadata(metadata);

            LogOrganization organization = new LogOrganization();
            organization.setParentLogIDs(new HashSet<>());
            organization.setChildLogIDs(new HashSet<>());
            organization.setTagIDs(new HashSet<>());
            root.setLogOrganization(organization);

            root.setLogContents(new ArrayList<>());
            root.setLogDisplayOverride(null);

            return repository.save(root);
        } else {
            throw new Exception("Root LogModel already created. id: '" + list.get(0).getId() + "'");
        }
    }

    public void validateNotRoot(LogModel model) throws Exception {
        String name = model.getMetadata().getName();
        if (name.equals(ROOT_NAME)) {
            throw new Exception("LogModel.metadata.name cannot be '" + ROOT_NAME + "'");
        }
    }

    public LogModel getRoot() throws Exception {
        List<LogModel> models = logModelGetService.findByName(ROOT_NAME);
        if (models.size() == 1) {
            return logModelGetService.findByName(ROOT_NAME).get(0);
        } else if (models.size() > 1) {
            // TODO log that they are multiple roots
            return logModelGetService.findByName(ROOT_NAME).get(0);
        } else {
            throw new Exception("Missing Root LogModel");
        }
    }
}
