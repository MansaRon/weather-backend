package co.za.demo.business;



import co.za.demo.business.base.AbstractService;
import co.za.demo.dto.LoginDTO;
import co.za.demo.dto.UserCreateDTO;

import co.za.demo.model.base.Entity;
import co.za.demo.model.base.Login;
import co.za.demo.model.base.User;
import co.za.demo.security.PwdEncryptionProvider;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;



@Singleton
public class UserService extends AbstractService {

    @Inject
    private PwdEncryptionProvider provider;


    public Mono<Entity> createUser(@NotNull @Valid UserCreateDTO userCreateDTO) {

        var user = mapper.map(User.class, userCreateDTO);

        if (userCreateDTO.getPwd() != null) {
            provider.setPassword(userCreateDTO.getPwd(), user);
        }

        return store.save(user);
    }






}
