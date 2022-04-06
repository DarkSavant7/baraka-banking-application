package org.darksavant.test.bank.util;

import org.darksavant.test.bank.api.dto.AccountDto;
import org.darksavant.test.bank.api.dto.CurrencyDto;
import org.darksavant.test.bank.api.dto.OperationDto;
import org.darksavant.test.bank.api.dto.RoleDto;
import org.darksavant.test.bank.api.dto.UserDto;
import org.darksavant.test.bank.domain.model.Account;
import org.darksavant.test.bank.domain.model.Currency;
import org.darksavant.test.bank.domain.model.Operation;
import org.darksavant.test.bank.domain.model.Role;
import org.darksavant.test.bank.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper for DTOs. Yes, I could use something like mapstruct, but that way I also needed to write some mapping code
 */
@Component
public class DtoMapper {

    public RoleDto roleToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public CurrencyDto currencyToDto(Currency currency) {
        return CurrencyDto.builder()
                .id(currency.getId())
                .name(currency.getName())
                .rate(currency.getRate())
                .build();
    }

    public AccountDto accountToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .amount(account.getAmount())
                .userId(account.getUser().getId())
                .currency(currencyToDto(account.getCurrency()))
                .build();
    }

    public UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .status(user.getStatus())
                .roles(user.getRoles().stream().map(this::roleToDto).collect(Collectors.toList()))
                .accounts(user.getAccounts().stream().map(this::accountToDto).collect(Collectors.toList()))
                .build();
    }

    public OperationDto operationToDto(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .amount(operation.getAmount())
                .account(accountToDto(operation.getAccount()))
                .transferAccount(operation.getTransferAccount().getId())
                .type(operation.getType())
                .build();
    }
}
