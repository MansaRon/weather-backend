package co.za.demo.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Introspected
@ToString
public class UserCreateDTO implements Serializable {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits starting with 0")
    private String phone;

    private char[] pwd;
}
