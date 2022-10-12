package com.food.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    private final BigDecimal ammount;

    public Money(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public boolean isGreaterThanZero(){
        return Objects.nonNull(this.ammount) && this.ammount.compareTo(BigDecimal.ZERO)  >0;
    }

    public boolean insGreaterThan(Money money){
        return Objects.nonNull(this.ammount) && this.ammount.compareTo(money.getAmmount()) > 0;
    }

    public Money add(Money money){
        return new Money(setScale(this.ammount.add(money.getAmmount())));
    }

    public Money substract(Money money){
        return new Money(setScale(this.ammount.subtract(money.getAmmount())));
    }

    public Money multiply(int multiplay){
        return new Money(setScale(this.ammount.multiply(new BigDecimal(multiplay))));
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return getAmmount().equals(money.getAmmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmmount());
    }

    private BigDecimal setScale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
