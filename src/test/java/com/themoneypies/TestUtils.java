package com.themoneypies;

import com.themoneypies.domain.Entry;
import com.themoneypies.parser.*;
import com.themoneypies.util.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TestUtils {
    public static String getFileText(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(TestUtils.class.getResource(fileName).getFile())));
        StringBuffer text = new StringBuffer("");
        String line;
        while ((line = br.readLine()) != null) {
            text.append(line).append("\n");
        }
        return text.toString();
    }

    public static List<Entry> getTestEntries(String entriesFilename) throws IOException {
        BT24EntryParser parser = new BT24EntryParser();
        List<Entry> entries = parser.getEntries(FileUtils.getFileText(entriesFilename));
        return entries;
    }
}
