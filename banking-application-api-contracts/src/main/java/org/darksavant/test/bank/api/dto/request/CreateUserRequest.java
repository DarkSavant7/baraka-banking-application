package org.darksavant.test.bank.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for user creation")
public class CreateUserRequest {

    @Length(message = "Must be in 4..255 digits", min = 3, max = 255)
    @NotBlank(message = "Username must NOT be empty")
    @Schema(name = "username", description = "User's username")
    private String username;

    @Length(message = "Must be in 8..255 digits", min = 3, max = 255)
    @NotBlank(message = "Password must NOT be empty")
    @Schema(name = "password", description = "User's password")
    private String password;
}
