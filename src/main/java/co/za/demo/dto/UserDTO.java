package co.za.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits starting with 0")
    private String phone;

    /**
     * User roles
     */
    private Set<String> roles;
}
