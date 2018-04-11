package com.loggerproject.coreservice.server.service.data.log.get.search.model;

import com.loggerproject.coreservice.server.service.data.log.get.type.detail.LogDetailModel;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class SearchResponse extends ResourceSupport {
    List<LogDetailModel> logDetails;
}
