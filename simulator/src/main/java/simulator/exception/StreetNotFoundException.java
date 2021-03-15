package simulator.exception;

public class StreetNotFoundException extends RuntimeException {
    public StreetNotFoundException(String message) {
        super(message);
    }
}
