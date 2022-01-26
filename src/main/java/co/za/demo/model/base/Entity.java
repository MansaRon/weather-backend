package co.za.demo.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class Entity extends Persistable {

    /**
     * Unique identifier, optimized for database queries and referencing.
     */
    @BsonId
    @NotNull
    private ObjectId id;

    /**
     * Human readable unique number for the instance
     */
    @NotNull
    private String reference;

    /**
     * Short description defining the instance.
     */
    @NotBlank
    private String description;

    /**
     * Timestamp when the record was created in the database.
     */
    @NotNull
    private LocalDateTime createdAt;

    /**
     * Timestamp when the record was last updated in the database.
     */
    @NotNull
    private LocalDateTime updatedAt;

    public String hexId() {
        return id != null ? id.toHexString() : null;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
