package org.darksavant.test.bank.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for user creation")
public class CreateAccountRequest {

    @NotNull
    @Schema(name = "userId", description = "Account owner ID")
    private Long userId;

    @Schema(name = "currencyId", description = "Account currency ID")
    private Long currencyId;
}
