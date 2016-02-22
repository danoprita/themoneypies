package com.themoneypies.web;

import com.themoneypies.domain.Rule;
import com.themoneypies.repository.RuleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RuleController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RuleRepository ruleRepository;

    @RequestMapping("/rules")
    @ResponseBody
    public List<Rule> rules() throws JsonProcessingException {
        return ruleRepository.findAllByOrderByVersionDesc();
    }

    @RequestMapping(value ="/rules", method = RequestMethod.POST)
    @ResponseBody
    public Rule createRule(@RequestBody RuleCommand ruleCommand) {
        Rule rule = ruleRepository.findRuleByTag(ruleCommand.getTag());
        if (rule == null) {
            rule = new Rule();
            rule.setTag(ruleCommand.getTag());
        }
        rule.getPatterns().add(ruleCommand.getPattern());
        rule.setExclude(ruleCommand.getExclude());
        rule.setSavings(ruleCommand.getSavings());
        ruleRepository.save(rule);
        return rule;
    }

    @RequestMapping(value = "/rules/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        Rule rule = ruleRepository.findOne(id);
        if (rule != null) {
            ruleRepository.delete(rule);
        }
        return "{}";
    }
}
