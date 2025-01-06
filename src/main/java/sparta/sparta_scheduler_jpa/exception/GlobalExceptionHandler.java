package sparta.sparta_scheduler_jpa.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiErrorResponse> handleLoginException(LoginException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ApiErrorResponse> handleValidateException(ValidateException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(LocalDateTime.now(), exception.getHttpStatus().value(),
                exception.getHttpStatus().getReasonPhrase(), exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiErrorResponse errorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Validation Failed", errors.toString(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPasswordExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiErrorResponse errorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Invalid Password", errors.toString(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleScheduleNotFoundException(ScheduleNotFoundException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Schedule Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(ResponseStatusException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                exception.getStatusCode().value(),
                exception.getReason(),
                exception.getReason(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                exception.getMessage(),
                request.getRequestURI()
        );

        log.error("Unhandled exception occurred", exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        exception.getConstraintViolations().forEach(violation -> {
            String fieldName = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
            errors.put(fieldName, violation.getMessage());
        });

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors.toString(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDataAccessException(DataAccessException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database Error",
                "An error occurred while accessing the database.",
                request.getRequestURI()
        );

        log.error("Database access error", exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON Request",
                "The request body is invalid or malformed.",
                request.getRequestURI()
        );

        log.error("Malformed JSON request", exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        StringBuilder message = new StringBuilder();
        message.append(exception.getMethod());
        message.append(" method is not supported for this request. Supported methods are ");
        exception.getSupportedHttpMethods().forEach(t -> message.append(t + " "));

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                message.toString(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpServletRequest request) {
        String message = exception.getParameterName() + " parameter is missing";

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Missing Parameter",
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        String message = String.format("Parameter '%s' should be of type '%s'", exception.getName(), exception.getRequiredType().getSimpleName());

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Parameter Type Mismatch",
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDateFormatException(InvalidDateFormatException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Date Format",
                exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidInputException(InvalidInputException exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Input",
                exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}