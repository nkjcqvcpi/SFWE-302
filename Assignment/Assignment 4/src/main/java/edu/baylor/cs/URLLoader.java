package edu.baylor.cs;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class URLLoader {

    public final void loadData() {
        URL url = null;
        BufferedReader in = null;
        try {
            url = new URL("https://sample-videos.com/csv/Sample-Spreadsheet-1000-rows.csv");
// this works on Cerny's try UTF-8 if messy chars
            in = new BufferedReader(new InputStreamReader(url.openStream(),
                    StandardCharsets.ISO_8859_1));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Task 4
                // processLine(inputLine.split(","));

                // Task 7
                processLine(split(inputLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Task 2
//    protected abstract void processLine(String tokens);
    // Task 4
    protected abstract void processLine(String[] tokens);

    protected String[] split(String inputLine) {
        String[] tokens = inputLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].contains("\"")) {
                tokens[i] = tokens[i].replaceAll("\"\"", "\"");
                tokens[i] = tokens[i].replaceAll("^\"", ""); // beginning
                tokens[i] = tokens[i].replaceAll("\"$", ""); // end
            }
        }
        return tokens;
    }
}
