package com.themoneypies.web;

import com.themoneypies.domain.Entry;
import com.themoneypies.domain.Rule;
import com.themoneypies.report.ReportGenerator;
import com.themoneypies.report.SavingsResult;
import com.themoneypies.repository.EntryRepository;
import com.themoneypies.repository.RuleRepository;
import com.themoneypies.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportGenerator reportGenerator;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    RuleRepository ruleRepository;

    @RequestMapping("/report-savings")
    @ResponseBody
    public SavingsResult savings(
            @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate
    ) {
        List<Entry> entries = entryRepository.findAll();
        return reportGenerator.savings(entries, startDate, endDate);
    }

    @RequestMapping("/report-expenses-per-tag")
    @ResponseBody
    public Map<String, BigDecimal> expensesPerTag(
            @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate
    ) {
        List<Entry> entries = entryRepository.findAll();
        List<Rule> rules = ruleRepository.findAll();
        return reportGenerator.expensesPerTag(entries, rules, startDate, endDate);
    }

    @RequestMapping("/report-savings-per-month")
    @ResponseBody
    public Map<String, SavingsResult> savingsPerMonth(
            @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate
    ) {
        List<Entry> entries = entryRepository.findAll();
        return reportGenerator.savingsPerMonth(entries, startDate, endDate);
    }

    @RequestMapping("/report-tags-per-month")
    @ResponseBody
    public TagsPerMonthResponse tagsPerMonth(
            @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate
    ) {
        List<Entry> entries = entryRepository.findAll();
        List<Rule> rules = ruleRepository.findAll();
        return reportGenerator.tagsPerMonth(entries, rules, startDate, endDate);
    }
}
