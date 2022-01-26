package co.za.demo.repository.mongo;


import co.za.demo.model.base.CollectionName;
import co.za.demo.model.base.Entity;
import co.za.demo.repository.RepositoryException;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.annotation.PostConstruct;

import static io.micronaut.core.util.StringUtils.isEmpty;
import static java.util.Objects.isNull;

@Singleton
public final class MongoConfig {

    @Inject
    private MongoClient mongoClient;

    @Value("${service.datastore.collection-prefix}")
    private String collectionPrefix;

    @Value("${service.datastore.name}")
    private String databaseName;

    @Value("${micronaut.data.paging.max-page-size}")
    private int maxPageSize;

    @Value("${micronaut.data.paging.default-page}")
    private int defaultPage;


    private MongoDatabase database;

    public <T extends Entity> String collectionName(Class<T> clazz) {
        var annotation = clazz.getAnnotation(CollectionName.class);
        if (isNull(annotation)) throw new RepositoryException(String.format(
                "@Collection is required in '%s' class", clazz.getSimpleName()));

        var collectionName = annotation.value().trim();
        if (isEmpty(collectionName)) throw new RepositoryException(String.format(
                "Invalid collection name '%s' in '%s' class", collectionName, clazz.getSimpleName()));
        return collectionPrefix + "." + collectionName.toLowerCase();
    }

    public <T extends Entity> MongoCollection<T> collection(Class<T> type) {
        return database.getCollection(collectionName(type), type);
    }

    public MongoDatabase getDatabase() { return database; }

    public int getMaxPageSize() {return maxPageSize;}

    public int getDefaultPage() {return defaultPage;}

    @PostConstruct
    void init() {
        database = mongoClient.getDatabase(databaseName);
    }
}


