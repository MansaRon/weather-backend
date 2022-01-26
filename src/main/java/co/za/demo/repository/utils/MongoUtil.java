package co.za.demo.repository.utils;

import co.za.demo.repository.RepositoryException;
import org.bson.types.ObjectId;


public final class MongoUtil {

    public static ObjectId toId(String hex) {
        if (ObjectId.isValid(hex)) return new ObjectId(hex);
        throw new RepositoryException(String.format("Invalid entity id '%s'", hex));
    }
}

