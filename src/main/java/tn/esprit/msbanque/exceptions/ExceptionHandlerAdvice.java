package tn.esprit.msbanque.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    // Gérer les erreurs de validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");

        response.put("errors", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage())));

        return response;
    }

    // Gérer les violations de contraintes
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Constraint Violation");
        response.put("message", ex.getMessage());
        return response;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid Argument");
        response.put("message", ex.getMessage());
        return response;
    }

    // Gérer les clés dupliquées
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleDuplicateKey(DuplicateKeyException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request)
                .withDetail("Un enregistrement avec cette clé existe déjà")
                .build();
    }

    // Gérer les erreurs de référence de propriété
    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handlePropertyReference(PropertyReferenceException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request)
                .withDetail("Propriété non trouvée: " + ex.getPropertyName())
                .build();
    }

    // Gérer toutes les autres exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> response = buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request)
                .withDetail("Une erreur inattendue s'est produite")
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Méthode pour construire la réponse d'erreur
    private ErrorResponseBuilder buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        return new ErrorResponseBuilder()
                .withTimestamp(LocalDateTime.now())
                .withStatus(status.value())
                .withError(status.getReasonPhrase())
                .withMessage(ex.getMessage())
                .withPath(request.getDescription(false).replace("uri=", ""));
    }

    // Classe interne pour construire la réponse d'erreur
    private static class ErrorResponseBuilder {
        private final Map<String, Object> response = new HashMap<>();

        ErrorResponseBuilder withTimestamp(LocalDateTime timestamp) {
            response.put("timestamp", timestamp);
            return this;
        }

        ErrorResponseBuilder withStatus(int status) {
            response.put("status", status);
            return this;
        }

        ErrorResponseBuilder withError(String error) {
            response.put("error", error);
            return this;
        }

        ErrorResponseBuilder withMessage(String message) {
            response.put("message", message);
            return this;
        }

        ErrorResponseBuilder withPath(String path) {
            response.put("path", path);
            return this;
        }

        ErrorResponseBuilder withDetail(String detail) {
            response.put("detail", detail);
            return this;
        }

        // Méthode qui retourne la réponse en tant que Map
        Map<String, Object> build() {
            return response;
        }
    }
}
