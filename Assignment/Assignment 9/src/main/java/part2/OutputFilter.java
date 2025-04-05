package part2;

import java.io.*;

public class OutputFilter implements Runnable {
    private PipedReader input;

    public OutputFilter(PipedReader input) {
        this.input = input;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(input)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}