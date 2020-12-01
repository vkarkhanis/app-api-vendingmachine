package com.api.vendingmachine.store;

import com.api.vendingmachine.models.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money, Integer> {
}
