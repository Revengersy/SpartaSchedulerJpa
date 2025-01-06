package sparta.sparta_scheduler_jpa.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
