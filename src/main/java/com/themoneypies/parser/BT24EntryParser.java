package com.themoneypies.parser;

import au.com.bytecode.opencsv.CSVParser;
import com.themoneypies.domain.Entry;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class BT24EntryParser implements EntryParser {

    @Override
    public List<Entry> getEntries(String text) {
        List<Entry> entries = new ArrayList<Entry>();

        String[] lines = text.split("\\n");
        try {
            for (String line : lines) {
                if (isLineAGoodEntry(line)) {
                    Entry entry = getEntry(line);
                    entries.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private Entry getEntry(String line) throws IOException, ParseException {
        CSVParser csvParser = new CSVParser();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");

        Entry entry = new Entry();
        String[] values = csvParser.parseLine(line);
        entry.setDate(formatter.parse(values[0]));
        entry.setDescription(values[2]);
        entry.setReference(values[3]);

        if (!values[4].isEmpty()) {
            entry.setDebit(new BigDecimal(values[4].replaceAll(",", "")));
        } else {
            entry.setDebit(new BigDecimal(0.00));
        }

        if (!values[5].isEmpty()) {
            entry.setCredit(new BigDecimal(values[5].replaceAll(",", "")));
        } else {
            entry.setCredit(new BigDecimal(0.00));
        }

        return entry;
    }

    public boolean isLineAGoodEntry(String line) throws IOException {
        CSVParser csvParser = new CSVParser();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");

        String[] values = csvParser.parseLine(line);
        try {
            return values.length == 7 &&
                    !values[0].isEmpty() &&
                    !values[2].isEmpty() &&
                    formatter.parse(values[0]) != null;
        } catch (ParseException e) {
           return false;
        }
    }
}
