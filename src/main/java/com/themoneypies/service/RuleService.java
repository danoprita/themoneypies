package com.themoneypies.service;

import com.themoneypies.domain.Rule;

import java.util.List;

public interface RuleService {
    boolean isPersisted(Rule rule);
    void importRules(List<Rule> rules);
}
