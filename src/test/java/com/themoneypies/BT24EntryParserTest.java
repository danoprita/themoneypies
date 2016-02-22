package com.themoneypies;

import com.themoneypies.parser.BT24EntryParser;
import com.themoneypies.domain.Entry;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class BT24EntryParserTest {

    @Test
    public void getEntries() throws IOException {
        BT24EntryParser parser = new BT24EntryParser();
        String text = TestUtils.getFileText("/bt24-credit-card.csv");
        List<Entry> entries = parser.getEntries(text);
        assertNotNull(entries);
        assertEquals(13, entries.size());
    }
}
