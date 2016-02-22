package com.themoneypies.report;

import com.themoneypies.domain.Entry;
import com.themoneypies.domain.Rule;
import com.themoneypies.service.EntryService;
import com.themoneypies.web.TagsPerMonthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ReportGeneratorImpl implements ReportGenerator {

    @Autowired
    EntryService entryService;

    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }

    @Override
    public SavingsResult savings(List<Entry> entries, Date startDate, Date endDate) {
        BigDecimal income = new BigDecimal(0);
        BigDecimal expenses = new BigDecimal(0);
        BigDecimal deposits = new BigDecimal(0);

        for (Entry entry : entries) {
            if (isBetween(entry.getDate(), startDate, endDate)) {
                if (!entryService.shouldBeExcluded(entry)) {
                    income = income.add(entry.getCredit());
                    if (entryService.isSavings(entry)) {
                        deposits = deposits.add(entry.getDebit());
                    } else {
                        expenses = expenses.add(entry.getDebit());
                    }
                }
            }
        }

        SavingsResult result = new SavingsResult();
        result.setIncome(income.toPlainString());
        result.setExpenses(expenses.toPlainString());
        result.setSavings(income.add(expenses).toPlainString());
        result.setDeposits(deposits.abs().toPlainString());
        return result;
    }

    private boolean isBetween(Date date, Date startDate, Date endDate) {
        return startDate.compareTo(date) <= 0 && date.compareTo(endDate) <= 0;
    }

    @Override
    public Map<String, BigDecimal> expensesPerTag(List<Entry> entries, List<Rule> rules, Date startDate, Date endDate) {
        Map<String, BigDecimal> tags = new TreeMap<String, BigDecimal>();

        List<Entry> entriesWithoutTags = new ArrayList<Entry>();
        for (Entry entry : entries) {
            if (isBetween(entry.getDate(), startDate, endDate)) {
                if (!entryService.shouldBeExcluded(entry)) {
                    boolean hasRule = false;
                    for (Rule rule : rules) {
                        if (rule.appliesTo(entry)) {
                            hasRule = true;
                            BigDecimal currentAmount = tags.get(rule.getTag());
                            if (currentAmount == null) {
                                tags.put(rule.getTag(), entry.getDebit());
                            } else {
                                tags.put(rule.getTag(), currentAmount.add(entry.getDebit()));
                            }
                        }
                    }
                    if (!hasRule) {
                        entriesWithoutTags.add(entry);
                    }
                }
            }
        }

        if (entriesWithoutTags.size() > 0) {
            BigDecimal sum = new BigDecimal(0);
            for (Entry entry : entriesWithoutTags) {
                sum = sum.add(entry.getDebit());
            }
            tags.put("other", sum);
        }

        return tags;
    }

    @Override
    public Map<String, SavingsResult> savingsPerMonth(List<Entry> entries, Date startDate, Date endDate) {
        Map<String, SavingsAndExpenses> savingsPerMonth = new TreeMap<String, SavingsAndExpenses>();

        for (Entry entry : entries) {
            if (isBetween(entry.getDate(), startDate, endDate)) {
                if (!entryService.shouldBeExcluded(entry)) {
                    String key = getMonthKey(entry.getDate());
                    SavingsAndExpenses savingsAndExpenses = savingsPerMonth.get(key);
                    if (savingsAndExpenses == null) {
                        savingsAndExpenses = new SavingsAndExpenses();
                        savingsPerMonth.put(key, savingsAndExpenses);
                    }
                    savingsAndExpenses.setIncome(savingsAndExpenses.getIncome().add(entry.getCredit()));
                    if (entryService.isSavings(entry)) {
                        savingsAndExpenses.setDeposits(savingsAndExpenses.getDeposits().add(entry.getDebit().abs()));
                    } else {
                        savingsAndExpenses.setExpenses(savingsAndExpenses.getExpenses().add(entry.getDebit()));
                    }
                }
            }
        }

        Map<String, SavingsResult> savingsResultMap = new TreeMap<String, SavingsResult>();
        for (String key : savingsPerMonth.keySet()) {
            SavingsResult savingsResult = new SavingsResult();
            SavingsAndExpenses savingsAndExpenses = savingsPerMonth.get(key);
            savingsResult.setExpenses(savingsAndExpenses.getExpenses().toPlainString());
            savingsResult.setIncome(savingsAndExpenses.getIncome().toPlainString());
            savingsResult.setSavings(savingsAndExpenses.getIncome().add(savingsAndExpenses.getExpenses())
                    .toPlainString());
            savingsResult.setDeposits(savingsAndExpenses.getDeposits().toPlainString());
            savingsResultMap.put(key, savingsResult);
        }

        return savingsResultMap;
    }

    @Override
    public TagsPerMonthResponse tagsPerMonth(List<Entry> entries, List<Rule> rules, Date startDate, Date endDate) {
        Map<String, Map<String, BigDecimal>> tagsPerMonth = new TreeMap<String, Map<String, BigDecimal>>();
        Set<String> tagsSet = new HashSet<String>();

        Map<String, List<Entry>> entriesPerMonth = getEntriesPerMonth(entries, startDate, endDate);
        for (String key : entriesPerMonth.keySet()) {
            Map<String, BigDecimal> debitPerTag = expensesPerTag(entriesPerMonth.get(key), rules, startDate, endDate);
            tagsSet.addAll(debitPerTag.keySet());
            tagsPerMonth.put(key, debitPerTag);
        }

        TagsPerMonthResponse tagsPerMonthResponseResponse = new TagsPerMonthResponse();
        tagsPerMonthResponseResponse.setTagsSet(tagsSet);
        tagsPerMonthResponseResponse.setTagsPerMonth(tagsPerMonth);

        return tagsPerMonthResponseResponse;
    }

    private Map<String, List<Entry>> getEntriesPerMonth(List<Entry> entries, Date startDate, Date endDate) {
        Map<String, List<Entry>> entriesPerMonth = new TreeMap<String, List<Entry>>();

        for (Entry entry : entries) {
            if (isBetween(entry.getDate(), startDate, endDate)) {
                if (!entryService.shouldBeExcluded(entry)) {
                    String key = getMonthKey(entry.getDate());

                    List<Entry> monthEntries = entriesPerMonth.get(key);
                    if (monthEntries == null) {
                        monthEntries = new ArrayList<Entry>();
                        entriesPerMonth.put(key, monthEntries);
                    }
                    monthEntries.add(entry);
                }
            }
        }

        return entriesPerMonth;
    }

    private String getMonthKey(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        return simpleDateFormat.format(date);
    }
}
