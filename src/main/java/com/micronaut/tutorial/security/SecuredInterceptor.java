package com.micronaut.tutorial.security;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class SecuredInterceptor implements MethodInterceptor<Object, Object> {

    public static final Logger logger = LoggerFactory.getLogger(SecuredInterceptor.class);

    @Inject
    protected SecurityService securityService;

    @Override
    public Object intercept(final MethodInvocationContext<Object, Object> context) {
        logger.trace("Intercepting: {}", context.getTargetMethod());

        context.findAnnotation(Secured.class).ifPresent(annot -> {
            boolean matches = false;
            List<String> rejectedAuthorities = new ArrayList<>();
            for (String authority : annot.getValue(String[].class).orElseThrow()) {
                matches = authority.equals(SecurityRule.IS_ANONYMOUS) || (
                        !(authority.equals(SecurityRule.DENY_ALL))
                                && !(authority.equals(SecurityRule.IS_AUTHENTICATED) && !securityService.isAuthenticated())
                                && !(!authority.equals(SecurityRule.IS_AUTHENTICATED) && !securityService.hasRole(authority))
                );
                if (!matches)
                    rejectedAuthorities.add(authority);
                else
                    break;
            }

            if (!matches) {
                logger.debug("Security rule rejects: {} for: {}", String.join(",", rejectedAuthorities),
                             context.getTargetMethod());
                throw new RuntimeException("Resource forbidden");
            }

        });

        return context.proceed();
    }
}
