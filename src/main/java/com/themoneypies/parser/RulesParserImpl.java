package com.themoneypies.parser;

import com.themoneypies.domain.Rule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RulesParserImpl implements RulesParser {

    @Override
    public List<Rule> getRules(String text) {
        List<Rule> rules = new ArrayList<Rule>();

        String[] lines = text.split("\\n");
        for (String line : lines) {
            Rule rule = getRule(line);
            rules.add(rule);
        }
        return rules;
    }

    private Rule getRule(String line) {
        Rule rule = new Rule();
        String[] values = line.split(",");

        rule.setTag(values[0]);

        List<String> patterns = new ArrayList<String>();
        for (int i = 1; i < values.length; i++) {
            patterns.add(values[i]);
        }
        rule.setPatterns(patterns);

        return rule;
    }
}
