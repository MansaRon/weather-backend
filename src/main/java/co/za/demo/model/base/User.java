package co.za.demo.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@CollectionName("demo.users")
public class User extends Entity{


    @NotBlank
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10,
            message = "Phone number should be 10 digits starting with 0")
    private String phone;

    @JsonIgnore
    @NotNull
    private byte[] password;

    @JsonIgnore
    @NotNull
    private byte[] salt;

    /**
     * User roles assigned to
     */
    private Set<String> roles;

    public void addRoles(String... newRoles) {
        if (roles == null) roles = new HashSet<>();
        roles.addAll(Arrays.asList(newRoles));
    }

    public void removeRoles(String... newRoles) {
        if (roles == null) return;
        Arrays.asList(newRoles).forEach(roles::remove);
    }

}
