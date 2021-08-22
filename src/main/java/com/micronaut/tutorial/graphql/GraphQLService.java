package com.micronaut.tutorial.graphql;

import jakarta.inject.Qualifier;
import jakarta.inject.Singleton;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * All beans with this annotation represent the GraphQL service which handles the GraphQL fields.
 *
 * @author TCavka
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Singleton
public @interface GraphQLService {

}
