package com.themoneypies.report;

import java.math.BigDecimal;

public class SavingsAndExpenses {
    private BigDecimal income = new BigDecimal(0);
    private BigDecimal expenses = new BigDecimal(0);
    private BigDecimal deposits = new BigDecimal(0);

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getDeposits() {
        return deposits;
    }

    public void setDeposits(BigDecimal deposits) {
        this.deposits = deposits;
    }
}
