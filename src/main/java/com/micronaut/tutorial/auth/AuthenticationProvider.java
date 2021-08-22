package com.micronaut.tutorial.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.Flowable;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.List;

/**
 * Provides dummy users with passwords and authorities for Basic HTTP Auth.
 *
 * @author TCavka
 */
@Singleton
public class AuthenticationProvider implements io.micronaut.security.authentication.AuthenticationProvider {

    public static final String ROLE_USER  = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String[] USERS = new String[]{
            "user", "userpass", ROLE_USER,
            "admin", "adminpass", ROLE_ADMIN
    };

    @Override
    public Publisher<AuthenticationResponse> authenticate(final HttpRequest<?> httpRequest, final AuthenticationRequest<?, ?> authenticationRequest) {
        for (int i = 0; i < USERS.length; i += 3)
            if (authenticationRequest.getIdentity().equals(USERS[i]) && authenticationRequest.getSecret().equals(USERS[i + 1]))
                return Flowable.just(AuthenticationResponse.success("user", List.of(USERS[i + 2])));

        return Flowable.just(new AuthenticationFailed());
    }
}
