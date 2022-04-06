package org.darksavant.test.bank.repository;

import org.darksavant.test.bank.domain.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("select c from Currency c where c.name = :name")
    Currency findByName(@Param("name") String name);
}
