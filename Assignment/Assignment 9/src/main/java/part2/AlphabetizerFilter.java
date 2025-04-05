package part2;

import java.io.*;
import java.util.*;

public class AlphabetizerFilter implements Runnable {
    private PipedReader input;
    private PipedWriter output;

    public AlphabetizerFilter(PipedReader input, PipedWriter output) {
        this.input = input;
        this.output = output;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(input);
             BufferedWriter bw = new BufferedWriter(output)) {
            List<String> shifts = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                shifts.add(line);
            }
            // Sort shifts alphabetically (ignoring case).
            Collections.sort(shifts, String.CASE_INSENSITIVE_ORDER);
            for (String shift : shifts) {
                bw.write(shift);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { output.close(); } catch (IOException e) { }
        }
    }
}
