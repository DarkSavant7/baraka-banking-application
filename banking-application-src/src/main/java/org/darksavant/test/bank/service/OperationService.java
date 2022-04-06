package org.darksavant.test.bank.service;

import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.OperationDto;
import org.darksavant.test.bank.api.dto.request.DepositMoneyRequest;
import org.darksavant.test.bank.api.dto.request.TransferMoneyRequest;
import org.darksavant.test.bank.api.dto.request.WithdrawMoneyRequest;

import java.util.List;

public interface OperationService {

    OperationDto findDtoById(Long id);
    List<OperationDto> findAllByUserId(Long userId);
    List<OperationDto> findAll();
    AccountDto closeAccount(Long accountId, Long transferAccountId);
    OperationDto transfer(TransferMoneyRequest request);
    OperationDto deposit(DepositMoneyRequest request);
    OperationDto withdraw(WithdrawMoneyRequest request);
}
