package sparta.sparta_scheduler_jpa.exception;

public class NotOwnerActionException extends RuntimeException {
    public NotOwnerActionException() {
        super("You are not allowed this.");
    }
}
