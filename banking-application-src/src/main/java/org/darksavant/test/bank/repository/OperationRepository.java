package org.darksavant.test.bank.repository;

import org.darksavant.test.bank.api.enums.OperationType;
import org.darksavant.test.bank.domain.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select distinct o from Operation o left join o.account.user first_user " +
            "left join o.transferAccount.user second_user where first_user.id = :userId or second_user.id = :userId")
    List<Operation> findAllByUserId(@Param(value = "userId") Long userId);

    @Query("select o from Operation o where o.type = :type")
    List<Operation> findAllByType(@Param("type") OperationType type);
}
