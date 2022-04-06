package org.darksavant.test.bank.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * Class for representing currency
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for currency")
public class CurrencyDto {

    @Schema(name = "id", description = "Currency ID")
    private Long id;

    @Schema(name = "name", description = "Currency name")
    private String name;

    @NotNull
    @Schema(name = "rate", description = "Currency to USD exchange rate")
    private BigDecimal rate;
}
