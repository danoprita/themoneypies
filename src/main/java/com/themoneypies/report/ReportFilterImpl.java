package com.themoneypies.report;

import com.themoneypies.domain.Entry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportFilterImpl implements ReportFilter {

    @Override
    public List<Entry> removeDuplicates(List<Entry> entries) {
        List<Entry> unique = new ArrayList<Entry>();
        for (Entry entry : entries) {
            if (!unique.contains(entry)) {
                unique.add(entry);
            }
        }
        return unique;
    }
}
