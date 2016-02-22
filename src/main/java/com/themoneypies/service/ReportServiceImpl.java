package com.themoneypies.service;

import com.themoneypies.domain.Entry;
import com.themoneypies.report.ReportGenerator;
import com.themoneypies.report.SavingsResult;
import com.themoneypies.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    ReportGenerator reportGenerator;

    @Override
    public SavingsResult getSavings(Date start, Date end) {
        Iterable<Entry> allEntries = entryRepository.findAll();
        List<Entry> entries = new ArrayList<Entry>();

        for (Entry entry : allEntries) {
            if (start.compareTo(entry.getDate()) <= 0 && entry.getDate().compareTo(end) <= 0) {
                entries.add(entry);
            }
        }

        return reportGenerator.savings(entries, start, end);
    }
}
