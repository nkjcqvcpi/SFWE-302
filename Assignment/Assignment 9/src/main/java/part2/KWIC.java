package part2;

import java.io.*;

public class KWIC {
    public static void main(String[] args) throws Exception {
        // Create the pipes connecting the filters.
        PipedWriter inputToShifter = new PipedWriter();
        PipedReader inputToShifterReader = new PipedReader(inputToShifter);

        PipedWriter shifterToAlpha = new PipedWriter();
        PipedReader shifterToAlphaReader = new PipedReader(shifterToAlpha);

        PipedWriter alphaToOut = new PipedWriter();
        PipedReader alphaToOutReader = new PipedReader(alphaToOut);

        // Create filter threads.
        Thread inputFilter = new Thread(new InputFilter("input.txt", inputToShifter));
        Thread shifterFilter = new Thread(new CircularShifterFilter(inputToShifterReader, shifterToAlpha));
        Thread alphaFilter = new Thread(new AlphabetizerFilter(shifterToAlphaReader, alphaToOut));
        Thread outputFilter = new Thread(new OutputFilter(alphaToOutReader));

        // Start the threads.
        inputFilter.start();
        shifterFilter.start();
        alphaFilter.start();
        outputFilter.start();
    }
}

