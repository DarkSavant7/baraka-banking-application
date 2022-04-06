package org.darksavant.test.bank.service;

import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.request.CreateAccountRequest;
import org.darksavant.test.bank.api.dto.request.DepositMoneyRequest;
import org.darksavant.test.bank.api.dto.request.TransferMoneyRequest;
import org.darksavant.test.bank.api.dto.request.WithdrawMoneyRequest;
import org.darksavant.test.bank.domain.model.Account;
import org.darksavant.test.bank.domain.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountDto createAccount(CreateAccountRequest request, User user);
    AccountDto findDtoById(Long id);
    List<AccountDto> findAllByUserId(User user);
    Account findById(Long id);
    AccountDto closeAccount(Account account);
    BigDecimal transfer(TransferMoneyRequest request);
    Account deposit(DepositMoneyRequest request);
    Account withdraw(WithdrawMoneyRequest request);
}
