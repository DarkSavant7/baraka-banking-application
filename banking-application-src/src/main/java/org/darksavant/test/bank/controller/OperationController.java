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
import org.darksavant.test.bank.domain.model.User;
import org.darksavant.test.bank.service.AccountService;
import org.darksavant.test.bank.service.AuthenticationFacade;
import org.darksavant.test.bank.service.OperationService;
import org.darksavant.test.bank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "api/v1")
public class OperationController implements OperationApi {

    private final OperationService operationService;
    private final AccountService accountService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Override
    public ResponseEntity<AccountDto> createAccount(CreateAccountRequest request) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        return ResponseEntity.ok(accountService.createAccount(request, user));
    }

    @Override
    public ResponseEntity<AccountDto> closeAccount(Long accountId, Long transferAccountId) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        accountService.checkUserPermissions(accountId, user);
        return ResponseEntity.ok(operationService.closeAccount(accountId, transferAccountId));
    }

    @Override
    public ResponseEntity<AccountDto> getAccountById(Long accountId) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        accountService.checkUserPermissions(accountId, user);
        return ResponseEntity.ok(accountService.findDtoById(accountId));
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAccounts() {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        return ResponseEntity.ok(accountService.findAllByUserId(user));
    }

    @Override
    public ResponseEntity<OperationDto> getOperationById(Long operationId) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        operationService.checkUserPermissions(operationId, user);
        return ResponseEntity.ok(operationService.findDtoById(operationId));
    }

    @Override
    public ResponseEntity<List<OperationDto>> getOperations() {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        return ResponseEntity.ok(operationService.findAllByUserId(user.getId()));
    }

    @Override
    public ResponseEntity<OperationDto> transfer(TransferMoneyRequest request) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        operationService.checkUserPermissions(request.getFromAccountId(), user);
        return ResponseEntity.ok(operationService.transfer(request));
    }

    @Override
    public ResponseEntity<OperationDto> deposit(DepositMoneyRequest request) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        accountService.checkUserPermissions(request.getAccountId(), user);
        return ResponseEntity.ok(operationService.deposit(request));
    }

    @Override
    public ResponseEntity<OperationDto> withdraw(WithdrawMoneyRequest request) {
        User user = userService.findByUsername(authenticationFacade.getPrincipal().getName());
        accountService.checkUserPermissions(request.getAccountId(), user);
        return ResponseEntity.ok(operationService.withdraw(request));
    }
}
