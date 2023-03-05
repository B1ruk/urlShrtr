package io.b1ruk.start.urlShrtr.rest.controller;

import io.b1ruk.start.urlShrtr.model.UrlEntity;
import io.b1ruk.start.urlShrtr.rest.data.UrlData;
import io.b1ruk.start.urlShrtr.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("shortenUrl")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public Mono<ResponseEntity<UrlEntity>> shortenUrl(@RequestBody Mono<UrlData> urlData) {
        return urlService.shortenUrl(urlData);
    }

    @GetMapping("redirect")
    public Mono<ResponseEntity<Object>> redirectUrl(@RequestParam("url") String url) throws ExecutionException {
        return urlService.redirectUrl(url);
    }

}
