package error;

public class AnimalInvalidNameExcepion extends AnimalCreationException {
    public AnimalInvalidNameExcepion() {
        super("Animal name is invalid");
    }
}
