package org.darksavant.test.bank.repository;

import org.darksavant.test.bank.domain.model.Account;
import org.darksavant.test.bank.domain.model.Currency;
import org.darksavant.test.bank.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.user = :user")
    List<Account> findAllByUser(@Param("user") User user);

    @Query("select a from Account a where a.currency = :currency")
    List<Account> findAllByCurrency(@Param("currency") Currency currency);

    @Query("select a from Account a where a.currency = :currency and a.user = :user")
    List<Account> findAllByCurrencyAndUser(@Param("currency") Currency currency, @Param("user") User user);
}
