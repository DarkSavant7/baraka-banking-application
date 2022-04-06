package org.darksavant.test.bank.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.darksavant.test.bank.api.enums.OperationType;

import java.math.BigDecimal;


/**
 * Class for representing financial operation
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for operations")
public class OperationDto {

    @Schema(name = "id", description = "Operation ID")
    private Long id;

    @Schema(name = "amount", description = "Operation amount")
    private BigDecimal amount;

    @Schema(name = "account", description = "Operation account")
    private AccountDto account;

    @Schema(name = "transferAccount", description = "Operation transferAccount")
    private Long transferAccount;

    @Schema(name = "type", description = "Operation type")
    private OperationType type;
}
