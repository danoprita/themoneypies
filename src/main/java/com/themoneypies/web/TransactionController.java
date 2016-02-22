package com.themoneypies.web;

import com.themoneypies.domain.Entry;
import com.themoneypies.repository.EntryRepository;
import com.themoneypies.service.EntryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EntryService entryService;

    @RequestMapping("/transactions")
    @ResponseBody
    public List<Entry> transactions() throws JsonProcessingException {
        List<Entry> transactions = new ArrayList<Entry>();
        for (Entry entry : entryRepository.findAllByOrderByDateDesc()) {
            entryService.addMatchingRules(entry);
            transactions.add(entry);
        }
        return transactions;
    }
}
