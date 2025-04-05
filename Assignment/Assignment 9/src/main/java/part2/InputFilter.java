package part2;

import java.io.*;

public class InputFilter implements Runnable {
    private String inputFile;
    private PipedWriter output;

    public InputFilter(String inputFile, PipedWriter output) {
        this.inputFile = inputFile;
        this.output = output;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { output.close(); } catch (IOException e) { }
        }
    }
}