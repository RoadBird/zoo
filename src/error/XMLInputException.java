package error;

import java.io.IOException;

public class XMLInputException extends IOException {
    public XMLInputException() {
        super("Exception of load xml-file");
    }

    public XMLInputException(String message) {
        super(message);
    }
}
