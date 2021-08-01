package org.hta.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    public static final String ERROR_GENERIC = "# Unhandled \n" +
            "No se ha manejado una excepción correctamente y este error indica que no hay más";


    public static final String ERROR_GENERIC_V2 = "Error Generico en el servicio";

    public static final String NOT_FOUND = "Error no se encontro datos";


    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> exceptionConstraint(HttpServletRequest req, DataIntegrityViolationException exception) {
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause();
        SQLException sqlException = (SQLException) constraintViolationException.getCause();

        log.error("Exception error handler " + "exceptionConstraint" + " error http : " + UNPROCESSABLE_ENTITY);

        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(constraintViolationException.getMessage())
                .exception(sqlException.getMessage())
                .error("ERR-200")
                .time(LocalDateTime.now())
                .path(req.getServletPath())
                .build();

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errorResponse);
    }


    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = javax.validation.ConstraintViolationException.class)
    public ResponseEntity<?> exceptionConstraintValidation(HttpServletRequest req, javax.validation.ConstraintViolationException exception) {

        log.error("Exception error handler " + "exceptionConstraintValidation" + " error http : " + UNPROCESSABLE_ENTITY);

        List<ErrorDetail> errorDetails = exception.getConstraintViolations()
                .stream()
                .map(error -> ErrorDetail
                        .builder()
                        .field(error.getPropertyPath().toString())
                        .value(error.getInvalidValue().toString())
                        .message(error.getMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Error de validation de campos")
                .error("ERR-400")
                .errorType(ErrorType.TECHNICAL_ERROR)
                .status(UNPROCESSABLE_ENTITY)
                .errorType(ErrorType.BUSINESS_ERROR)
                .time(LocalDateTime.now())
                .path(req.getServletPath())
                .method(req.getMethod())
                .details(errorDetails)
                .build();

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {

        log.error("Exception error handler " + "handleMethodArgumentNotValid" + " error http : " + BAD_REQUEST);

        List<ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorDetail
                        .builder()
                        .field(error.getField())
                        .value(Objects.nonNull(error.getRejectedValue()) ? error.getRejectedValue().toString() : "")
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .method(ex.getMessage())
                        .error("ERR-400")
                        .errorType(ErrorType.BUSINESS_ERROR)
                        .status(BAD_REQUEST)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .details(errorDetails)
                        .build();

        return ResponseEntity.status(BAD_REQUEST).body(errorHandler);
    }


    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(CONFLICT)
    protected ResponseEntity<?> handleConflict(RuntimeException ex, HttpServletRequest req) {
        log.error("Exception error handler " + "handleConflict" + " error http : " + CONFLICT);

        String bodyOfResponse = "This should be application specific";
        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message(bodyOfResponse)
                        .exception(ex.getMessage())
                        .error("ERR-501")
                        .errorType(ErrorType.TECHNICAL_ERROR)
                        .status(CONFLICT)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .build();

        return ResponseEntity.status(CONFLICT).body(errorHandler);
    }

    @ExceptionHandler(value = {NullPointerException.class, RuntimeException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest req) {
        log.error("Exception error handler " + "handleAllExceptions" + " error http : " + INTERNAL_SERVER_ERROR);

        List<ErrorDetail> errorDetails = Arrays.stream(ex.getStackTrace())
                .map(error -> ErrorDetail
                        .builder()
                        .field(error.getFileName())
                        .value(error.getMethodName())
                        .message(error.getClassName())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message(ERROR_GENERIC)
                        .exception(ex.getMessage())
                        .error("ERR-500")
                        .errorType(ErrorType.TECHNICAL_ERROR)
                        .status(INTERNAL_SERVER_ERROR)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .details(errorDetails)
                        .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorHandler);

    }


    @ExceptionHandler(value = DataAccessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleAllExceptions(DataAccessException ex, HttpServletRequest req) {

        log.error("Exception error handler " + "handleAllExceptions" + " error http : " + INTERNAL_SERVER_ERROR);

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message(ERROR_GENERIC_V2)
                        .exception(ex.getMessage())
                        .error("ERR-502")
                        .errorType(ErrorType.TECHNICAL_ERROR)
                        .status(INTERNAL_SERVER_ERROR)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorHandler);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> responseDefault(NotFoundException ex, HttpServletRequest req) {
        log.error("Exception error handler " + "responseDefault" + " error http : " + NOT_FOUND);

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message(NOT_FOUND)
                        .exception(ex.getMessage())
                        .error("ERR-202")
                        .errorType(ErrorType.BUSINESS_ERROR)
                        .status(HttpStatus.NOT_FOUND)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .build();

        return new ResponseEntity<>(errorHandler, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> errorUnauthorized(UsernameNotFoundException ex, HttpServletRequest req) {
        log.error("Exception error handler " + "errorUnauthorized" + " error http : " + UNAUTHORIZED);

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message("User not found or not authorized")
                        .exception(ex.getMessage())
                        .error("ERR-202")
                        .errorType(ErrorType.BUSINESS_ERROR)
                        .status(HttpStatus.UNAUTHORIZED)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .build();

        return new ResponseEntity<>(errorHandler, HttpStatus.UNAUTHORIZED);

    }

    @ResponseBody
    @ExceptionHandler(CurrencyExchangeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> notFoundHandler(CurrencyExchangeNotFound ex, HttpServletRequest req) {
        log.error("Exception error handler " + "notFoundHandler" + " error http : " + NOT_FOUND);

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message("Currency not found")
                        .exception(ex.getMessage())
                        .error("ERR-407")
                        .errorType(ErrorType.BUSINESS_ERROR)
                        .status(HttpStatus.UNAUTHORIZED)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorHandler);
    }

    @ResponseBody
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> errorWebClient(WebClientResponseException ex, HttpServletRequest req) {
        HttpStatus httpStatus = ex.getStatusCode();
        log.error("Exception error handler " + "errorWebClient" + " error http : " + httpStatus.name());

        ErrorResponse errorHandler =
                ErrorResponse
                        .builder()
                        .message("Error Client Http")
                        .exception(ex.getMessage())
                        .error("ERR-600")
                        .errorType(ErrorType.TECHNICAL_ERROR)
                        .status(httpStatus)
                        .time(LocalDateTime.now())
                        .path(req.getServletPath())
                        .method(req.getMethod())
                        .build();

        return ResponseEntity
                .status(httpStatus)
                .body(errorHandler);
    }
}
