package org.darksavant.test.bank.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * Class for representing role
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for role")
public class RoleDto {

    @Schema(name = "id", description = "Role ID")
    private Long id;

    @NotNull
    @Schema(name = "name", description = "Role name")
    private String name;
}
