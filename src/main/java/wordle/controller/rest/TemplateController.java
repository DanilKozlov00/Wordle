package wordle.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

public interface TemplateController {

    String ERROR = "Error";

    default ResponseEntity<?> createOkResponseEntity(Map<String, Object> responseBody) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseBody);
    }

    default ResponseEntity<?> createOkResponseEntity(Object responseBody) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseBody);
    }

    default ResponseEntity<?> createErrorResponseEntity(String errorMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(Collections.singletonMap(ERROR, errorMessage));
    }

}