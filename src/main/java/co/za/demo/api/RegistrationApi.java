package co.za.demo.api;

import co.za.demo.business.UserService;
import co.za.demo.dto.LoginDTO;
import co.za.demo.dto.UserCreateDTO;
import co.za.demo.dto.UserDTO;
import co.za.demo.model.base.Login;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;


import javax.annotation.security.PermitAll;
import javax.validation.Valid;


@Controller("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
public class RegistrationApi extends API {

    @Inject
    private UserService service;

    @PermitAll
    @Post("/test")
    public Mono<String> test()  {
        return Mono.just("Registration is workiing....");
    }

    @PermitAll
    @Post("/register")
    public Mono<UserDTO> register(@Body @Valid UserCreateDTO userCreateDTO)  {
        var create = service.createUser(userCreateDTO);
        return create.map(user -> mapper.map(UserDTO.class, user));
    }


}