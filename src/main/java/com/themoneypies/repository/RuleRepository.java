package com.themoneypies.repository;

import com.themoneypies.domain.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepository extends CrudRepository<Rule, Long> {
    List<Rule> findAll();
    List<Rule> findAllByOrderByVersionDesc();
    Rule findRuleByTag(String tag);
    List<Rule> findByExclude(Boolean exclude);
}
