package com.micronaut.tutorial.graphql;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLInputField;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.metadata.strategy.InclusionStrategy;
import io.leangen.graphql.util.ClassUtils;
import jakarta.inject.Singleton;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Defines the rules which entity/DTO fields are considered in client input/output frames.
 *
 * @author TCavka
 */
@Singleton
public class GraphQLInclusionStrategy implements InclusionStrategy {

    @Override
    public boolean includeOperation(final AnnotatedElement element, final AnnotatedType type) {
        return ClassUtils.hasAnnotation(element, GraphQLQuery.class) || ClassUtils.hasAnnotation(element, GraphQLMutation.class);
    }

    @Override
    public boolean includeArgument(final Parameter parameter, final AnnotatedType type) {
        return ClassUtils.hasAnnotation(parameter, GraphQLArgument.class);
    }

    @Override
    public boolean includeInputField(final Class<?> declaringClass, final AnnotatedElement element, final AnnotatedType elementType) {
        // This is a bit more complex because it defines fields visibility for mutations, and this happens for setters, and if setter is
        // not present it happens on getters. We define here a one clean contract to include object properties as input fields:
        // it has to be setter with GraphQLInputField annotation.

        if (element instanceof Method && ((Method) element).getName().startsWith("set"))
            return ClassUtils.hasAnnotation(element, GraphQLInputField.class);

        return false;
    }
}
