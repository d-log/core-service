package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DirectoryModelRepository extends MongoRepository<DirectoryModel, String> {
    List<DirectoryModel> findByName(String name);
    List<DirectoryModel> findByNameStartingWith(String str);
    List<DirectoryModel> findByNameEndingWith(String str);

    /**
     * all users that have names containing substring `str` and order the results by created in ascending order
     * @param str
     * @return
     */
    Page<DirectoryModel> findByNameLikeOrderByMetadata_CreatedAsc(String str, Pageable pageable);
    Page<DirectoryModel> findByNameLikeOrderByMetadata_CreatedDesc(String str, Pageable pageable);

    List<DirectoryModel> findByMetadata_CreatedBetween(Date createdDateGT, Date createDateLT, Pageable pageable);

    @Query("{ 'name' : ?0 }")
    List<DirectoryModel> findByNameQuery(String name);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<DirectoryModel> findByNameRegexQuery(String regexp);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<DirectoryModel> findByNameRegexQueryList(String regexp, Pageable pageable);

    @Query("{ 'name' : { $regex: ?0 } }")
    Page<DirectoryModel> findByNameRegexQueryPage(String regexp, Pageable pageable);
}
