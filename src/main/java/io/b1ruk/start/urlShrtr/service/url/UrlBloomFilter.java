package io.b1ruk.start.urlShrtr.service.url;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.b1ruk.start.urlShrtr.model.UrlEntity;
import io.b1ruk.start.urlShrtr.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class UrlBloomFilter {

    private final UrlRepository urlRepository;

    private BloomFilter<String> shortenedUrlsBloomFilter;

    public UrlBloomFilter(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostConstruct
    public void init() {

        List<UrlEntity> urlEntities = urlRepository.findAll().collectList().block();

        shortenedUrlsBloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), urlEntities.size() * 2, 0.01);

        urlEntities.stream()
                .map(UrlEntity::getUrl)
                .forEach(url -> shortenedUrlsBloomFilter.put(url));
    }

    public void updateFilter(UrlEntity urlEntity) {
        shortenedUrlsBloomFilter.put(urlEntity.getUrl());
    }

    public boolean isUniqueUrl(String url) {
        return !shortenedUrlsBloomFilter.mightContain(url);
    }
}
