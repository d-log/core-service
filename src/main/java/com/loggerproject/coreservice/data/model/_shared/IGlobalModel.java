package com.loggerproject.coreservice.data.model._shared;

public interface IGlobalModel {

    void setId(String id);

    String getId();

    void setMetadata(Metadata metadata);

    Metadata getMetadata();
}
