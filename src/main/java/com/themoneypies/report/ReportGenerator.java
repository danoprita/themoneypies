package com.themoneypies.report;

import com.themoneypies.domain.Entry;
import com.themoneypies.domain.Rule;
import com.themoneypies.web.TagsPerMonthResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportGenerator {
    SavingsResult savings(List<Entry> entries, Date startDate, Date endDate);
    Map<String, BigDecimal> expensesPerTag(List<Entry> entries, List<Rule> rules, Date startDate, Date endDate);
    Map<String, SavingsResult> savingsPerMonth(List<Entry> entries, Date startDate, Date endDate);
    TagsPerMonthResponse tagsPerMonth(List<Entry> entries, List<Rule> rules, Date startDate, Date endDate);
}
