package com.themoneypies.parser;

import com.themoneypies.domain.Rule;

import java.util.List;

public interface RulesParser {
    List<Rule> getRules(String text);
}
