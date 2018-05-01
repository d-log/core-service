package com.loggerproject.coreservice.server.service.filedata.type.log.get.type;

import com.loggerproject.coreservice.server.data.document.file.FileModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ALogTypeGetService<T extends ALogTypeModel> {

    public List<T> getByLogModels(Collection<FileModel> lfs) {
        List<T> logTiles = new ArrayList<>();
        for (FileModel lf : lfs) {
            T logTile = getByLogModel(lf);
            logTiles.add(logTile);
        }
        return logTiles;
    }

    public T getByLogModel(FileModel lf) {
        if (lf == null) {
            return null;
        } else {
            return getByLogModelInternal(lf);
        }
    }

    public abstract T getByLogModelInternal(FileModel lf);
}
