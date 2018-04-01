package com.loggerproject.coreservice.service.data.log.get.search.model;

import com.loggerproject.coreservice.service.data.log.get.detail.model.LogDetailModel;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class SearchResponse extends ResourceSupport {
    List<LogDetailModel> logDetails;
}
