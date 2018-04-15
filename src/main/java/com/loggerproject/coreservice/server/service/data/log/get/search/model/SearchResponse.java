package com.loggerproject.coreservice.server.service.data.log.get.search.model;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * ResourceSupport is a Hateoas feature which is configured at controller layer
 */
@Data
public class SearchResponse extends ResourceSupport {
    List<GlobalModel> logs;
}
