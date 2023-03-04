package io.b1ruk.start.urlShrtr.rest.controller;

import io.b1ruk.start.urlShrtr.rest.data.UrlData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("shortenUrl")
public class UrlController {

    @GetMapping("redirect/{url}")
    public Mono<ResponseEntity<Void>> redirectUrl(@PathVariable("url") String url) {
        //TODO call redirect service
        Mono<UrlData> urlData = null;
        ResponseEntity<Void> redirectResponse = ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, "").build();
        return Mono.just(redirectResponse);
    }

    public Mono<ResponseEntity<UrlData>> shortenUrl(@RequestBody Mono<UrlData> urlData) {
        //TODO shorten url
        return Mono.just(null);
    }
}
