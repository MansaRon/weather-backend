package co.za.demo.repository.mongo;


import co.za.demo.model.base.Entity;
import co.za.demo.model.base.User;
import co.za.demo.repository.Repository;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.conversions.Bson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

import static co.za.demo.repository.utils.MongoUtil.toId;
import static com.mongodb.client.model.Filters.eq;
import static java.lang.Math.min;
import static java.util.Objects.isNull;


@Singleton
public class MongoRepository implements Repository {

    @Value("${micronaut.data.paging.max-page-size}")
    private int maxPageSize;

    @Value("${micronaut.data.paging.default-page}")
    private int defaultPage;

    @Inject
    private MongoConfig config;

    public <T extends Entity> MongoCollection<T> collection(Class<T> clazz) {
        return config.collection(clazz);
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Entity> Mono<T> save(T entity) {
        return Mono.from(collection((Class<T>) entity.getClass())
                        .insertOne(entity))
                .map(it -> entity);
    }

    @Override
    public <T extends Entity> Mono<T> get(Class<T> type, String id) {
        return Mono.from(collection(type)
                .find(eq("_id", toId(id)))
                .limit(1));
    }

    @Override
    public <T extends Entity> Flux<T> get(Class<T> type, Pageable pageable) {
        // limiting
        final var pageNumber = isNull(pageable.getPageNumber()) ?
                config.getDefaultPage() : pageable.getPageNumber();
        final var pageSize = isNull(pageable.getPageSize()) ?
                config.getMaxPageSize() : min(pageable.getPageSize(), config.getMaxPageSize());

        // filtering
        final var filter = pageable.getFilter();
        final var clause = clause(filter.getType(), filter.getValues());

        //sorting
        final var sortBy = pageable.getSortBy();
        final var sorting = isNull(sortBy) ? Filters.empty() :
                pageable.asc() ? Sorts.ascending(sortBy) : Sorts.descending(sortBy);

        return Flux.from(collection(type)
                .find(clause)
                .sort(sorting)
                .skip(pageNumber)
                .limit(pageSize));
    }

    private Bson clause(FilterType type, Map<String, Object> fieldValues) {
        if (fieldValues == null) return Filters.empty();

        final var conditions = fieldValues.entrySet()
                .stream()
                .map(e -> Filters.eq(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return switch (type) {
            case AND -> Filters.and(conditions);
            case OR -> Filters.or(conditions);
        };
    }


}
