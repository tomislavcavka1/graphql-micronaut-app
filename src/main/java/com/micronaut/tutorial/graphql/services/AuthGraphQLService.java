package com.micronaut.tutorial.graphql.services;

import com.micronaut.tutorial.graphql.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.Map;

/**
 * {@link GraphQLService} returning information about currently logged-in user.
 */
@GraphQLService
public class AuthGraphQLService {

    @Inject
    protected SecurityService securityService;

    @GraphQLQuery
    public Object authUser() {
        return securityService.getAuthentication().map(auth -> {
            return Map.of("username", auth.getName(),
                          "roles", auth.getRoles());
        }).orElse(Map.of());
    }
}
