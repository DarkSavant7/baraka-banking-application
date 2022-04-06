package org.darksavant.test.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.OperationDto;
import org.darksavant.test.bank.api.dto.request.DepositMoneyRequest;
import org.darksavant.test.bank.api.dto.request.TransferMoneyRequest;
import org.darksavant.test.bank.api.dto.request.WithdrawMoneyRequest;
import org.darksavant.test.bank.api.enums.OperationType;
import org.darksavant.test.bank.domain.model.Account;
import org.darksavant.test.bank.domain.model.Operation;
import org.darksavant.test.bank.error.NotFoundException;
import org.darksavant.test.bank.repository.OperationRepository;
import org.darksavant.test.bank.service.AccountService;
import org.darksavant.test.bank.service.OperationService;
import org.darksavant.test.bank.util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository repository;
    private final DtoMapper mapper;
    private final AccountService accountService;


    @Override
    @Transactional
    public OperationDto findDtoById(Long id) {
        Operation operation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Operation with id " + id + " not found"));
        return mapper.operationToDto(operation);
    }

    @Override
    @Transactional
    public List<OperationDto> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(mapper::operationToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<OperationDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::operationToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountDto closeAccount(Long accountId, Long transferAccountId) {
        Account account = accountService.findById(accountId);
        if (account.getAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
            transfer(accountId, transferAccountId, account.getAmount());
        }
        return accountService.closeAccount(account);
    }

    private void transfer(Long from, Long to, BigDecimal amount) {
        TransferMoneyRequest request = TransferMoneyRequest.builder()
                .amount(amount)
                .fromAccountId(from)
                .toAccountId(to)
                .build();
        transfer(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationDto transfer(TransferMoneyRequest request) {
        log.info("Transfer start {}", request);
        BigDecimal incomeAmount = accountService.transfer(request);
        Operation outcome = Operation.builder()
                .amount(request.getAmount())
                .account(accountService.findById(request.getFromAccountId()))
                .transferAccount(accountService.findById(request.getToAccountId()))
                .type(OperationType.OUTCOME_TRANSFER)
                .build();

        Operation income = Operation.builder()
                .amount(incomeAmount)
                .account(accountService.findById(request.getToAccountId()))
                .transferAccount(accountService.findById(request.getFromAccountId()))
                .type(OperationType.INCOME_TRANSFER)
                .build();
        repository.save(income);
        outcome = repository.save(outcome);
        log.info("Transfer finish {}", request);
        return mapper.operationToDto(outcome);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationDto deposit(DepositMoneyRequest request) {
        log.info("Deposit start {}", request);
        Operation operation = Operation.builder()
                .amount(request.getAmount())
                .account(accountService.deposit(request))
                .type(OperationType.DEPOSIT)
                .build();

        operation = repository.save(operation);
        log.info("Deposit finish {}", request);
        return mapper.operationToDto(operation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationDto withdraw(WithdrawMoneyRequest request) {
        log.info("Withdraw start {}", request);
        Operation operation = Operation.builder()
                .amount(request.getAmount())
                .account(accountService.withdraw(request))
                .type(OperationType.WITHDRAWAL)
                .build();

        operation = repository.save(operation);
        log.info("Withdraw finish {}", request);
        return mapper.operationToDto(operation);
    }
}
