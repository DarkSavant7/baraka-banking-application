package org.darksavant.test.bank.service;

import org.darksavant.test.bank.domain.model.Currency;

public interface CurrencyService {

    Currency findById(Long id);
}
