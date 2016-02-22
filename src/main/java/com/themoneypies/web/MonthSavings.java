package com.themoneypies.web;

import com.themoneypies.report.SavingsResult;

public class MonthSavings {
    private SavingsResult savingsResult;
    private String month;

    public SavingsResult getSavingsResult() {
        return savingsResult;
    }

    public void setSavingsResult(SavingsResult savingsResult) {
        this.savingsResult = savingsResult;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
