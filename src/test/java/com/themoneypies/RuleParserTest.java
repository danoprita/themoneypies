package com.themoneypies;

import com.themoneypies.domain.Rule;
import com.themoneypies.parser.RulesParser;
import com.themoneypies.parser.RulesParserImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class RuleParserTest {

    @Test
    public void getPatterns() throws IOException {
        RulesParser rulesParser = new RulesParserImpl();
        String text = TestUtils.getFileText("/rules.csv");

        List<Rule> rules = rulesParser.getRules(text);
        assertNotNull(rules);
        assertEquals(3, rules.size());
    }
}
