package newsApp.exception.userException;

public class AlreadyExistingUserException extends UserException {
    public AlreadyExistingUserException() {
        super();
    }

    public AlreadyExistingUserException(String message) {
        super(message);
    }
}
