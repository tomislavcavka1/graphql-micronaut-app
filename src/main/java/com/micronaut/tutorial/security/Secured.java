package com.micronaut.tutorial.security;

import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;
import io.micronaut.security.rules.SecurityRule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Around
@Type(SecuredInterceptor.class)
public @interface Secured {

    String[] value() default SecurityRule.IS_ANONYMOUS;
}
