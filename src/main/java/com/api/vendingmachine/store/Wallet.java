package com.api.vendingmachine.store;

import com.api.vendingmachine.models.DenominationType;
import com.api.vendingmachine.models.Money;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Wallet {

    @Autowired
    private MoneyRepository moneyRepository;

    private Map<Float, Money> billCash;
    private Map<Float, Money> coinCash;

    public Wallet() {
        this.billCash = new HashMap<>();
        this.coinCash = new HashMap<>();
    }

    public float updateWallet(Money money) {
        return 0.0f;
    }

    public List<Money> calculateRefund(float chargedAmount, String requestId) {

        return null;
    }

    public float populateWallet() {

        return 0.0f;
    }

    public float calculateBalance() {

        List<Money> money = null;

        if(this.billCash == null || this.billCash.size() == 0 ) {
          money = fetchAllMoney();
            this.billCash = money.stream().filter(eachBill -> eachBill.getDenominationType() == DenominationType.Bill)
                  .collect(Collectors.toMap(Money::getValue, eachMoney -> eachMoney));
        }

        if(this.coinCash == null || this.coinCash.size() == 0 ) {

            if (money == null) {
                money = fetchAllMoney();
            }

            this.coinCash = money.stream().filter(eachBill -> eachBill.getDenominationType() == DenominationType.Coin)
                    .collect(Collectors.toMap(Money::getValue, eachMoney -> eachMoney));
        }


        float billCash =  (float)this.billCash.values().stream().mapToDouble((eachMoney) -> eachMoney.getValue() * eachMoney.getNumber()).sum();
        float coinCash =  (float)this.coinCash.values().stream().mapToDouble((eachMoney) -> eachMoney.getValue() * eachMoney.getNumber()).sum();
        return billCash + coinCash;
    }

    private List<Money> fetchAllMoney() {
        return moneyRepository.findAll();
    }

}
