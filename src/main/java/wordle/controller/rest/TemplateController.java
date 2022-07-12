package wordle.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class TemplateController {

    public ResponseEntity<?> createObjectOkResponseEntity(Object responseBody) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseBody);
    }

    public ResponseEntity<?> createStringOkResponseEntity(Object responseBody) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response(responseBody));
    }

    public ResponseEntity<?> createResponseEntityWithHttpStatus(String errorMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(new Response(errorMessage));
    }

    protected class Response {
        public Object response;

        public Response(Object response) {
            this.response = response;
        }

        public Object getResponse() {
            return response;
        }

        public void setResponse(Object response) {
            this.response = response;
        }
    }
}