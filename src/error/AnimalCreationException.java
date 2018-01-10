package error;

public class AnimalCreationException extends RuntimeException {
    public AnimalCreationException() {
        super("Animal cannot be created");
    }

    public AnimalCreationException(String message) {
        super(message);
    }

    public AnimalCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnimalCreationException(Throwable cause) {
        super(cause);
    }

    public AnimalCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
