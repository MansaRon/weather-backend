package co.za.demo.repository;


import co.za.demo.model.base.Entity;
import co.za.demo.repository.mongo.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

public interface Repository {

    <T extends Entity> Mono<T> save(@Valid  T candidate);

    <T extends Entity> Flux<T> get(Class<T> type, Pageable pageable);

    <T extends Entity> Mono<T> get(Class<T> type, String id);

}
