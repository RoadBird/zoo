package error;

public class AnimalInvalidSizeException extends AnimalCreationException {
    public AnimalInvalidSizeException() {
        super("Animal size is invalid");
    }
}
