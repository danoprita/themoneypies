package com.themoneypies.parser;

import com.themoneypies.domain.Entry;

import java.util.List;

public interface EntryParser {
    List<Entry> getEntries(String text);
}
