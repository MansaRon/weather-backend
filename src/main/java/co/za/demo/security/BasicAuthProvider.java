package co.za.demo.security;

import co.za.demo.model.base.User;

import co.za.demo.repository.mongo.EntityFilter;
import co.za.demo.repository.mongo.FilterType;
import co.za.demo.repository.mongo.MongoRepository;


import co.za.demo.repository.mongo.Pageable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static io.micronaut.security.authentication.AuthenticationFailureReason.*;


@Singleton
public class BasicAuthProvider implements AuthenticationProvider {

    @Inject
    private MongoRepository repository;

    @Inject
    private PwdEncryptionProvider provider;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        final String username = authenticationRequest.getIdentity().toString();
        final String password = authenticationRequest.getSecret().toString();

        var filter = EntityFilter
                .builder()
                .values(Map.of("email", username))
                .type(FilterType.AND)
                .build();

        final var pageable = Pageable.builder().filter(filter).build();

        return repository.get(User.class, pageable)
                .switchIfEmpty(Mono.error(() -> new AuthenticationException(
                        AuthenticationResponse.failure(USER_NOT_FOUND))))
                .map(user -> {
                    boolean isAuth = false;
                    try {

                        isAuth = provider.authenticate(password.toCharArray(),
                                user.getPassword(), user.getSalt());
                    } catch (InvalidKeySpecException ignored) {
                    }

                    if (isAuth) {
                        return AuthenticationResponse.success((String) authenticationRequest.getIdentity(),
                                user.getRoles());
                    }
                    return AuthenticationResponse.failure(CREDENTIALS_DO_NOT_MATCH);

                });
    }
}
