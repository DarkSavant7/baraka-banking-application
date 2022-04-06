package org.darksavant.test.bank.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.darksavant.test.bank.api.enums.UserStatus;

import java.util.List;


/**
 * Class for representing user

 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for user")
public class UserDto {

    @Schema(name = "id", description = "User id")
    private Long id;

    @Schema(name = "username", description = "User's username")
    private String username;

    @Schema(name = "accounts", description = "List of user's accounts")
    private List<AccountDto> accounts;

    @Schema(name = "status", description = "User status")
    private UserStatus status;

    @Schema(name = "roles", description = "List of users roles")
    private List<RoleDto> roles;

}
