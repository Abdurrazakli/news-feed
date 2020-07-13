package newsApp.exceptions;

public class NewsNotFound extends RuntimeException {
    public NewsNotFound(String message) {
        super(message);
    }
}
