package org.darksavant.test.bank.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.OperationDto;
import org.darksavant.test.bank.api.dto.request.CreateAccountRequest;
import org.darksavant.test.bank.api.dto.request.DepositMoneyRequest;
import org.darksavant.test.bank.api.dto.request.TransferMoneyRequest;
import org.darksavant.test.bank.api.dto.request.WithdrawMoneyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "OPERATIONS", description = "Set of endpoints to make operations with accounts")
public interface OperationApi {

    @Operation(summary = "Create account")
    @PostMapping("/account")
    ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request);

    @Operation(summary = "Close account")
    @DeleteMapping("/account")
    ResponseEntity<AccountDto> closeAccount(
            @Parameter(name = "accountId", description = "ID of the closing acc") @RequestParam Long accountId,
            @Parameter(name = "transferAccountId", description = "ID of the acc to transfer money from closing") @RequestParam Long transferAccountId);

    @Operation(summary = "Get account by id")
    @GetMapping("/account/{id}")
    ResponseEntity<AccountDto> getAccountById(@Parameter(name = "accountId", description = "ID of the acc") @PathVariable("id") Long accountId);

    @Operation(summary = "Get current user account")
    @GetMapping("/account")
    ResponseEntity<AccountDto> getAccount(@Parameter(hidden = true) Principal principal);

    @Operation(summary = "Get all accounts")
    @GetMapping("/account/all")
    ResponseEntity<List<AccountDto>> getAccounts();

    @Operation(summary = "Get operation by id")
    @GetMapping("/operation/{id}")
    ResponseEntity<OperationDto> getOperationById(@Parameter(name = "operationId", description = "ID of the operation") @PathVariable("id") Long operationId);

    @Operation(summary = "Get current user operations")
    @GetMapping("/operation")
    ResponseEntity<List<OperationDto>> getOperations(@Parameter(hidden = true) Principal principal);


    @Operation(summary = "Transfer")
    @PostMapping("/transfer")
    ResponseEntity<OperationDto> transfer(@RequestBody TransferMoneyRequest request);

    @Operation(summary = "Deposit")
    @PostMapping("/deposit")
    ResponseEntity<OperationDto> deposit(@RequestBody DepositMoneyRequest request);

    @Operation(summary = "Withdraw")
    @PostMapping("/wthdraw")
    ResponseEntity<OperationDto> withdraw(@RequestBody WithdrawMoneyRequest request);

}
