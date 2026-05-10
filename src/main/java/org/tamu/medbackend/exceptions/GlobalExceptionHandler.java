package org.tamu.medbackend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // =========================
    // BUSINESS EXCEPTION
    // =========================
    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                ex.getErrorCode().getStatus(),
                ex.getMessage()
        );

        enrich(pd, request);
        pd.setTitle(ex.getErrorCode().name());
        pd.setType(URI.create(typeUri(ex.getErrorCode().name())));

        return pd;
    }

    // =========================
    // VALIDATION ERROR
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            errors.put(field, err.getDefaultMessage());
        });

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("VALIDATION_ERROR");
        pd.setDetail("Request validation failed");
        pd.setProperty("errors", errors);

        enrich(pd, request);
        pd.setType(URI.create(typeUri("VALIDATION_ERROR")));

        return pd;
    }

    // =========================
    // SECURITY: ACCESS DENIED
    // =========================
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(Exception ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        pd.setTitle("FORBIDDEN");
        pd.setDetail("You do not have permission to access this resource");

        enrich(pd, request);
        pd.setType(URI.create(typeUri("FORBIDDEN")));

        return pd;
    }

    // =========================
    // DATABASE ERRORS
    // =========================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDB(Exception ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        pd.setTitle("DATABASE_CONSTRAINT_VIOLATION");
        pd.setDetail("Database constraint error occurred");

        enrich(pd, request);
        pd.setType(URI.create(typeUri("CONFLICT")));

        return pd;
    }

    // =========================
    // ILLEGAL ARGUMENT
    // =========================
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArg(Exception ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("BAD_REQUEST");
        pd.setDetail(ex.getMessage());

        enrich(pd, request);
        pd.setType(URI.create(typeUri("BAD_REQUEST")));

        return pd;
    }

    // =========================
    // FALLBACK (CATCH ALL)
    // =========================
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknown(Exception ex, HttpServletRequest request) {

        ex.printStackTrace();

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pd.setTitle("INTERNAL_ERROR");
        pd.setDetail("Unexpected server error");

        enrich(pd, request);
        pd.setType(URI.create(typeUri("INTERNAL_ERROR")));

        return pd;
    }

    // =========================
    // COMMON ENRICHMENT (ENTERPRISE STANDARD)
    // =========================
    private void enrich(ProblemDetail pd, HttpServletRequest request) {

        pd.setInstance(URI.create(request.getRequestURI()));

        pd.setProperty("timestamp", LocalDateTime.now());
        pd.setProperty("traceId", UUID.randomUUID().toString());
        pd.setProperty("service", "med-backend");
    }

    // =========================
    // TYPE GENERATOR (CLEAN API DESIGN)
    // =========================
    private String typeUri(String code) {
        return "https://api.medbackend.com/errors/" + code.toLowerCase();
    }
}
