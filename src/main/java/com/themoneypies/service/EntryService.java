package com.themoneypies.service;

import com.themoneypies.domain.Entry;
import com.themoneypies.domain.Rule;

import java.util.List;

public interface EntryService {
    boolean isPersisted(Entry entry);
    Entry addMatchingRules(Entry entry);

    List<Rule> getMatchingRules(Entry entry);

    boolean shouldBeExcluded(Entry entry);
    void importEntries(List<Entry> entries);
    boolean isSavings(Entry entry);
}
