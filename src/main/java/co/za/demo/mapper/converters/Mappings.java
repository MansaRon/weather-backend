package co.za.demo.mapper.converters;


import co.za.demo.dto.UserCreateDTO;
import co.za.demo.model.base.Entity;
import co.za.demo.model.base.User;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class Mappings {

    public void add(ModelMapper mapper) {
        userDTO(mapper);

    }

    public void userDTO(ModelMapper mapper) {
        mapper.addMappings(new PropertyMap<UserCreateDTO, User>() {
            @Override
            protected void configure() {
                using(hexId).map(ObjectId.get(), destination.getReference());
                map(LocalDateTime.now(), destination.getCreatedAt());
                map(LocalDateTime.now(), destination.getUpdatedAt());


            }
        });
    }


    private final Converter<ObjectId, String> hexId = (ctx)
            -> ctx.getSource().toHexString();

    private final Converter<List<? extends Entity>, List<String>> idHex =
            ctx -> ctx.getSource()
                    .stream()
                    .map(entity -> {
                        var id = entity.getId();
                        if (id == null)
                            throw new RuntimeException(String.format(
                                    "Entity '%s' must made persisted before mapping ID to hex",
                                    entity.getClass().getName()));
                        return id.toHexString();
                    })
                    .collect(Collectors.toList());
}
