package io.b1ruk.start.urlShrtr.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.b1ruk.start.urlShrtr.model.UrlEntity;
import io.b1ruk.start.urlShrtr.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Configuration
public class UrlCacheService {

    private final UrlRepository urlRepository;

    private Cache<String, Mono<UrlEntity>> urlEntityCache;

    public UrlCacheService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostConstruct
    public void init() {
        urlEntityCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.DAYS)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public Mono<UrlEntity> get(String shortenedUrl) throws ExecutionException {
        return urlEntityCache.get(shortenedUrl, () -> urlRepository.findById(shortenedUrl));
    }
}
