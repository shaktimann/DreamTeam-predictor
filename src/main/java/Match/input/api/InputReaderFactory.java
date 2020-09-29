package Match.input.api;

import Match.input.impl.CSVInputReader;
import Match.input.impl.ExcelInputReader;

public class InputReaderFactory {

    private static InputReaderFactory factory;

    private InputReaderFactory() {
    }

    public InputReader getInputReader(String inputFileName) {
        if (inputFileName.endsWith("xlsx") || inputFileName.endsWith("xls"))
            return new ExcelInputReader();
        else
            return new CSVInputReader();
    }

    public static InputReaderFactory getInputReaderFactoryInstance() {
        if (factory == null) {
            factory = new InputReaderFactory();
        }
        return factory;
    }

}
