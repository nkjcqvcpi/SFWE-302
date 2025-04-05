package part2;

import java.io.*;

public class CircularShifterFilter implements Runnable {
    private PipedReader input;
    private PipedWriter output;

    public CircularShifterFilter(PipedReader input, PipedWriter output) {
        this.input = input;
        this.output = output;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(input);
             BufferedWriter bw = new BufferedWriter(output)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                int n = words.length;
                // Generate each circular shift.
                for (int i = 0; i < n; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < n; j++) {
                        sb.append(words[(i + j) % n]);
                        if (j < n - 1) {
                            sb.append(" ");
                        }
                    }
                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { output.close(); } catch (IOException e) { }
        }
    }
}
