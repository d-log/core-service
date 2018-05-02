package com.loggerproject.coreservice.endpoint.api.extra;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrappers;

import java.util.Collection;
import java.util.Collections;

/**
 * https://github.com/spring-projects/spring-hateoas/issues/522
 */
@SuppressWarnings(value = "unchecked")
public class EmptiableResources extends Resources {
    public EmptiableResources(Class c, Collection content, Link... links) {
        super(content.isEmpty() ? Collections.singletonList(new EmbeddedWrappers(false).emptyCollectionOf(c)) : content, links);
    }
}
