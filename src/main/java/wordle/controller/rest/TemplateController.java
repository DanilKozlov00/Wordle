package wordle.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wordle.model.exceptions.ControllerException;

import java.util.Collections;
import java.util.Map;

public abstract class TemplateController {

    public ResponseEntity<?> createOkResponseEntity(Object responseBody) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseBody);
    }

    public ResponseEntity<?> createErrorResponseEntity(String errorMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(new ControllerException(errorMessage));
    }

}