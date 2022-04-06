package org.darksavant.test.bank.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


/**
 * Class for representing user account
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for account")
public class AccountDto {

    @Schema(name = "id", description = "Account id")
    private Long id;

    @Schema(name = "amount", description = "Account money amount in account currency")
    private BigDecimal amount;

    @Schema(name = "userId", description = "Account owner ID")
    private Long userId;

    @Schema(name = "currency", description = "Account currency")
    private CurrencyDto currency;

}
