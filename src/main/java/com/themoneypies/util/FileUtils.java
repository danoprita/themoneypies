package com.themoneypies.util;

import java.io.*;

public class FileUtils {

    public static String getFileText(byte[] bytes) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        return getText(br);
    }

    public static String getFileText(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        return getText(br);
    }

    private static String getText(BufferedReader br) throws IOException {
        StringBuffer text = new StringBuffer("");
        String line;
        while ((line = br.readLine()) != null) {
            text.append(line).append("\n");
        }
        return text.toString();
    }
}
