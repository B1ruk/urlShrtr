package io.b1ruk.start.urlShrtr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Mono<ResponseEntity> defaultExceptionHandler() {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Unable to process request"));
    }
}
