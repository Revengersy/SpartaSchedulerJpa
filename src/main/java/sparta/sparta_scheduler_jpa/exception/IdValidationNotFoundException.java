package sparta.sparta_scheduler_jpa.exception;

public class IdValidationNotFoundException extends RuntimeException {
    public IdValidationNotFoundException(Long id) {super("Does not found id = " + id);}
}
