package com.themoneypies.service;

import com.themoneypies.domain.Entry;
import com.themoneypies.domain.Rule;
import com.themoneypies.repository.EntryRepository;
import com.themoneypies.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public boolean isPersisted(Entry entry) {
        Iterable<Entry> entryIterable = entryRepository.findAll();

        boolean exists = false;
        for (Entry entry1 : entryIterable) {
            if (entry1.equals(entry)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    @Override
    public Entry addMatchingRules(Entry entry) {
        entry.getMatchingRules().addAll(getMatchingRules(entry));
        return entry;
    }

    @Override
    public List<Rule> getMatchingRules(Entry entry) {
        List<Rule> matchingRules = new ArrayList<Rule>();
        for (Rule rule : ruleRepository.findAll()) {
            if (rule.appliesTo(entry)) {
                matchingRules.add(rule);
            }
        }
        return matchingRules;
    }

    @Override
    public boolean shouldBeExcluded(Entry entry) {
        List<Rule> excludeRules = ruleRepository.findByExclude(true);

        boolean shouldBeExcluded = false;
        for (Rule rule : excludeRules) {
            if (rule.appliesTo(entry)) {
                shouldBeExcluded = true;
                break;
            }
        }

        return shouldBeExcluded;
    }

    @Override
    public void importEntries(List<Entry> entries) {
        for (Entry entry : entries) {
            if (!isPersisted(entry)) {
                entryRepository.save(entry);
            }
        }
    }

    @Override
    public boolean isSavings(Entry entry) {
        for (Rule rule : getMatchingRules(entry)) {
            if (rule.getSavings() != null && rule.getSavings()) {
                return true;
            }
        }
        return false;
    }
}
