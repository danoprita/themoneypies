package com.themoneypies.service;

import com.themoneypies.domain.Rule;
import com.themoneypies.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public boolean isPersisted(Rule rule) {
        Iterable<Rule> entryIterable = ruleRepository.findAll();

        boolean exists = false;
        for (Rule rule1 : entryIterable) {
            if (rule1.equals(rule)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    @Override
    public void importRules(List<Rule> rules) {
        for (Rule rule : rules) {
            if (!isPersisted(rule)) {
                ruleRepository.save(rule);
            }
        }
    }
}
