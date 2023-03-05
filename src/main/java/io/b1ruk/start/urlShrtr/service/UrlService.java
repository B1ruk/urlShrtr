package io.b1ruk.start.urlShrtr.service;

import io.b1ruk.start.urlShrtr.model.UrlEntity;
import io.b1ruk.start.urlShrtr.repository.UrlRepository;
import io.b1ruk.start.urlShrtr.rest.data.UrlData;
import io.b1ruk.start.urlShrtr.service.cache.UrlCacheService;
import io.b1ruk.start.urlShrtr.service.keyGenerator.KeyGeneratorService;
import io.b1ruk.start.urlShrtr.service.url.UrlBloomFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final KeyGeneratorService keyGeneratorService;
    private final UrlBloomFilter urlBloomFilter;
    private final UrlCacheService urlCacheService;

    public UrlService(UrlRepository urlRepository, KeyGeneratorService keyGeneratorService, UrlBloomFilter urlBloomFilter, UrlCacheService urlCacheService) {
        this.urlRepository = urlRepository;
        this.keyGeneratorService = keyGeneratorService;
        this.urlBloomFilter = urlBloomFilter;
        this.urlCacheService = urlCacheService;
    }

    public Mono<ResponseEntity<UrlEntity>> shortenUrl(Mono<UrlData> urlDataMono) {
        return urlDataMono.flatMap(this::createOrFindShortenedUrl)
                .flatMap(urlEntity -> Mono.just(ResponseEntity.ok()
                        .body(urlEntity)));
    }

    private Mono<UrlEntity> createOrFindShortenedUrl(UrlData urlData) {
        boolean uniqueUrl = urlBloomFilter.isUniqueUrl(urlData.url());

        if (uniqueUrl) {
            String shortenedUrl = keyGeneratorService.base62Encode(urlData.url());
            var urlEntity = new UrlEntity(shortenedUrl, urlData.url());
            urlBloomFilter.updateFilter(urlEntity);
            return urlRepository.save(urlEntity);
        }

        return urlRepository.findByUrl(urlData.url());
    }


    public Mono<ResponseEntity<Object>> redirectUrl(String shortenedUrl) throws ExecutionException {
        return urlCacheService.get(shortenedUrl)
                .flatMap(urlEntity -> {
                    ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, urlEntity.getUrl()).build();
                    return Mono.just(responseEntity);
                });
    }
}
