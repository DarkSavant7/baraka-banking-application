package org.darksavant.test.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.darksavant.test.bank.domain.model.Currency;
import org.darksavant.test.bank.error.NotFoundException;
import org.darksavant.test.bank.repository.CurrencyRepository;
import org.darksavant.test.bank.service.CurrencyService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repository;

    @Override
    public Currency findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Currency with ID " + id + " not found"));
    }
}
