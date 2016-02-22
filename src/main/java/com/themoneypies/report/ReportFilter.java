package com.themoneypies.report;

import com.themoneypies.domain.Entry;

import java.util.List;

public interface ReportFilter {
    List<Entry> removeDuplicates(List<Entry> entries);
}
