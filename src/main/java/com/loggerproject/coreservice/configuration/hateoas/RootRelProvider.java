package com.loggerproject.coreservice.configuration.hateoas;

import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.core.DefaultRelProvider;

/**
 * When an embedded resource is provided in a response using the {@code org.springframework.hateoas.Resources} model,
 * this provider can be configured at runtime to make any embedded values root
 */
public class RootRelProvider implements RelProvider {

    private DefaultRelProvider defaultRelProvider = new DefaultRelProvider();

    @Override
    public String getItemResourceRelFor(Class<?> type) {
        return "item";
    }

    @Override
    public String getCollectionResourceRelFor(Class<?> type) {
        return "collection";
    }

    @Override
    public boolean supports(Class<?> delimiter) {
        return defaultRelProvider.supports(delimiter);
    }
}
