package org.darksavant.test.bank.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for money withdrawal")
public class WithdrawMoneyRequest {

    @NotNull
    @Schema(name = "accountId", description = "Account to withdraw money")
    private Long accountId;

    @NotNull
    @Positive
    @Schema(name = "amount", description = "Amount to withdraw from account in account currency")
    private BigDecimal amount;
}
