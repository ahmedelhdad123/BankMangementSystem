package com.fawry.bankmangementsystem.repository;

import com.fawry.bankmangementsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    boolean existsByCardNumber(String cardNumber);
    Optional<Account> findByCardNumber(String cardNumber);
    List<Account> findByUserId(Long userId);

}
