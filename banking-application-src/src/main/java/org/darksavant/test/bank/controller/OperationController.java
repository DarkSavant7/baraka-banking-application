package org.darksavant.test.bank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.darksavant.test.bank.api.controller.OperationApi;
import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.OperationDto;
import org.darksavant.test.bank.api.dto.request.CreateAccountRequest;
import org.darksavant.test.bank.api.dto.request.DepositMoneyRequest;
import org.darksavant.test.bank.api.dto.request.TransferMoneyRequest;
import org.darksavant.test.bank.api.dto.request.WithdrawMoneyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "api/v1")
public class OperationController implements OperationApi {
    
    @Override
    public ResponseEntity<AccountDto> createAccount(CreateAccountRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<AccountDto> closeAccount(Long accountId, Long transferAccountId) {
        return null;
    }

    @Override
    public ResponseEntity<AccountDto> getAccountById(Long accountId) {
        return null;
    }

    @Override
    public ResponseEntity<AccountDto> getAccount(Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return null;
    }

    @Override
    public ResponseEntity<OperationDto> getOperationById(Long operationId) {
        return null;
    }

    @Override
    public ResponseEntity<List<OperationDto>> getOperations(Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<OperationDto> transfer(TransferMoneyRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<OperationDto> deposit(DepositMoneyRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<OperationDto> withdraw(WithdrawMoneyRequest request) {
        return null;
    }
}
