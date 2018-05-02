package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.document.file.FileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileModelRepository extends MongoRepository<FileModel, String> {

    List<FileModel> findByMetadata_type(String type);

    Page<FileModel> findByMetadata_type(String type, Pageable pageable);

    List<FileModel> findByMetadata_typeAndMetadata_name(String type, String name);
}
