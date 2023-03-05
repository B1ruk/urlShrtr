package io.b1ruk.start.urlShrtr.repository;

import io.b1ruk.start.urlShrtr.model.UrlEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

public interface UrlRepository extends ReactiveCassandraRepository<UrlEntity, String> {

    @AllowFiltering
    Mono<UrlEntity> findByUrl(String url);
}
