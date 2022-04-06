package org.darksavant.test.bank.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "THis is your request to log in")
@NoArgsConstructor
public class JwtRequest {
    @Schema(description = "Username", required = true, example = "doc1")
    private String username;

    @Schema(description = "Users password ", required = true, example = "111")
    private String password;
}
