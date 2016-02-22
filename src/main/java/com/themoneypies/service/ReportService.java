package com.themoneypies.service;

import com.themoneypies.report.SavingsResult;

import java.util.Date;

public interface ReportService {
    SavingsResult getSavings(Date start, Date end);
}
