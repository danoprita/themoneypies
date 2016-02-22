package com.themoneypies.web;

import au.com.bytecode.opencsv.CSVWriter;
import com.themoneypies.domain.Entry;
import com.themoneypies.domain.Rule;
import com.themoneypies.parser.BT24EntryParser;
import com.themoneypies.parser.RulesParser;
import com.themoneypies.report.ReportFilter;
import com.themoneypies.repository.EntryRepository;
import com.themoneypies.repository.RuleRepository;
import com.themoneypies.service.EntryService;
import com.themoneypies.service.RuleService;
import com.themoneypies.util.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Controller
public class ImportController {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    BT24EntryParser bt24EntryParser;

    @Autowired
    ReportFilter reportFilter;

    @Autowired
    EntryService entryService;

    @Autowired
    RulesParser rulesParser;

    @Autowired
    RuleService ruleService;

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    EntryRepository entryRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public
    @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/import/transactions", method = RequestMethod.POST)
    public
    @ResponseBody
    String importTrasactions(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileText = FileUtils.getFileText(file.getBytes());
                List<Entry> entries = bt24EntryParser.getEntries(fileText);
                List<Entry> unique = reportFilter.removeDuplicates(entries);
                entryService.importEntries(unique);

                return "ok";
            } catch (Exception e) {
                return "Upload failed => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }

    @RequestMapping(value = "/import/rules", method = RequestMethod.POST)
    public
    @ResponseBody
    String importRules(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileText = FileUtils.getFileText(file.getBytes());

                List<Rule> rules = rulesParser.getRules(fileText);
                ruleService.importRules(rules);

                return "ok";
            } catch (Exception e) {
                return "Upload failed => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }

    @RequestMapping(value = "/download-rules")
    public void downloadRules(HttpServletResponse response) throws IOException {

        String csvFileName = "rules.csv";

        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        CSVWriter writer = new CSVWriter(response.getWriter(), ',', CSVWriter.NO_QUOTE_CHARACTER);
        List<Rule> rules = ruleRepository.findAll();
        for (Rule rule : rules) {
            String[] line = rule.getCsvLine();
            writer.writeNext(line);
        }
        writer.close();
    }

    @RequestMapping(value = "/randomize-transactions")
    @ResponseBody
    public String randomizeTransactions(@RequestParam Integer randomParam) {
        randomizeEntries(randomParam);
        return "ok";
    }

    public void randomizeEntries(Integer randomParam) {
        for (Entry entry : entryRepository.findAll()) {
            transformEntry(entry, randomParam);
        }
    }

    private void transformEntry(Entry entry, Integer randomParam) {
        BigDecimal credit = entry.getCredit();
        BigDecimal debit = entry.getDebit();

        BigDecimal newCredit = transformValue(credit, randomParam);
        BigDecimal newDebit = transformValue(debit, randomParam);

        entry.setCredit(newCredit);
        entry.setDebit(newDebit);

        entryRepository.save(entry);
    }

    private BigDecimal transformValue(BigDecimal value, Integer randomParam) {
        BigDecimal newValue = value;

        if (value.intValue() != 0) {
            Random random = new Random();
            boolean shouldAdd = random.nextBoolean();

            newValue = value.divide(new BigDecimal(randomParam), 2, BigDecimal.ROUND_CEILING);

            int percent = new BigDecimal(0.4).multiply(newValue).intValue();

            BigDecimal toAddOrSubtract;
            if (percent != 0) {
                toAddOrSubtract = new BigDecimal(random.nextInt(Math.abs(percent)));
            } else {
                toAddOrSubtract = new BigDecimal(0);
            }

            if (shouldAdd) {
                newValue = newValue.add(toAddOrSubtract);
            } else {
                newValue = newValue.subtract(toAddOrSubtract);
            }
        }
        return newValue;
    }
}