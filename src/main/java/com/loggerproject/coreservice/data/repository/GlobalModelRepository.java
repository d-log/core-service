package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface GlobalModelRepository<T extends GlobalModel, ID extends Serializable> extends MongoRepository<T, ID> {
    List<T> findByMetadata_name(String name);

    Page<T> findByMetadata_name(String name, Pageable pageable);
}
