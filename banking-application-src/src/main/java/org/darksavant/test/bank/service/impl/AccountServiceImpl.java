package org.darksavant.test.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.request.CreateAccountRequest;
import org.darksavant.test.bank.api.dto.request.DepositMoneyRequest;
import org.darksavant.test.bank.api.dto.request.TransferMoneyRequest;
import org.darksavant.test.bank.api.dto.request.WithdrawMoneyRequest;
import org.darksavant.test.bank.api.enums.AccountStatus;
import org.darksavant.test.bank.domain.model.Account;
import org.darksavant.test.bank.domain.model.User;
import org.darksavant.test.bank.error.BadRequestException;
import org.darksavant.test.bank.error.InsufficientMoneyException;
import org.darksavant.test.bank.error.NotFoundException;
import org.darksavant.test.bank.repository.AccountRepository;
import org.darksavant.test.bank.service.AccountService;
import org.darksavant.test.bank.service.CurrencyService;
import org.darksavant.test.bank.util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final int SCALE = 2; //I think we need more than 2, but I'm not sure how much and not sure about conversion rules

    private final AccountRepository repository;
    private final DtoMapper mapper;
    private final CurrencyService currencyService;

    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountRequest request, User user) {
        log.info("Creating account: {}", request);
        Account account = Account.builder()
                .status(AccountStatus.ACTIVE)
                .amount(BigDecimal.ZERO)
                .currency(currencyService.findById(request.getCurrencyId()))
                .user(user)
                .build();
        account = repository.save(account);
        log.info("Created account with id {}", account.getId());
        return mapper.accountToDto(account);
    }

    @Override
    @Transactional
    public AccountDto findDtoById(Long id) {
        return mapper.accountToDto(findById(id));
    }

    @Override
    @Transactional
    public List<AccountDto> findAllByUserId(User user) {
        log.info("Search accounts for user with id {}", user.getId());
        return repository.findAllByUser(user).stream()
                .map(mapper::accountToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Account findById(Long id) {
        log.info("Search account with id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account with id " + id + " not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountDto closeAccount(Account account) {
        checkAccountNotClosed(account);
        account.setStatus(AccountStatus.CLOSED);
        Account result = repository.save(account);
        return mapper.accountToDto(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal transfer(TransferMoneyRequest request) {
        Account from = findById(request.getFromAccountId());
        Account to = findById(request.getToAccountId());
        return transfer(from, to, request.getAmount());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account deposit(DepositMoneyRequest request) {
        Account account = findById(request.getAccountId());
        checkAccountNotClosed(account);
        BigDecimal amount = request.getAmount().setScale(SCALE, RoundingMode.HALF_DOWN);
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new BadRequestException("You can't deposit zero or negative amount");
        }
        account.setAmount(account.getAmount().add(amount));
        return repository.save(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account withdraw(WithdrawMoneyRequest request) {
        Account account = findById(request.getAccountId());
        checkAccountNotClosed(account);
        BigDecimal amount = request.getAmount().setScale(SCALE, RoundingMode.HALF_DOWN);
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new BadRequestException("You can't withdraw zero or negative amount");
        }
        account.setAmount(account.getAmount().subtract(amount));
        return repository.save(account);
    }

    private BigDecimal transfer(Account from, Account to, BigDecimal sumInFromCurrency) {
        checkAccountNotClosed(from);
        checkAccountNotClosed(to);

        if (sumInFromCurrency.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new BadRequestException("You cant transfer amount of " + sumInFromCurrency);
        }

        if (sumInFromCurrency.compareTo(from.getAmount()) > 0) {
            throw new InsufficientMoneyException("Not enough money to transfer");
        }

        from.setAmount(from.getAmount().subtract(sumInFromCurrency));
        BigDecimal transferAmount;

        if (Objects.equals(from.getCurrency(), to.getCurrency())) {
            transferAmount = sumInFromCurrency;
        } else {
            BigDecimal usdEq = sumInFromCurrency.multiply(from.getCurrency().getRate());
            transferAmount = usdEq.divide(to.getCurrency().getRate(), SCALE, RoundingMode.HALF_DOWN);
        }
        to.setAmount(to.getAmount().add(transferAmount));
        repository.save(from);
        repository.save(to);
        return transferAmount;
    }

    private void checkAccountNotClosed(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new BadRequestException("Account with ID " + account.getId() + " closed!");
        }
    }
}
