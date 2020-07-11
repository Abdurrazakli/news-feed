package newsApp.exceptions.userException;

public class UsernameAlreadyExistsEx extends UserException {
    public UsernameAlreadyExistsEx() {
        super();
    }

    public UsernameAlreadyExistsEx(String message) {
        super(message);
    }
}
