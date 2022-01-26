package co.za.demo.mapper;


import co.za.demo.mapper.converters.Mappings;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.module.jdk8.Jdk8Module;
import org.modelmapper.module.jsr310.Jsr310Module;


@Factory
public class MapperFactory {

    @Inject
    private Mappings mappings;

    @Singleton
    public ModelMapper mapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE);

        mapper.registerModule(new Jsr310Module());
        mapper.registerModule(new Jdk8Module());

        mappings.add(mapper);
        return mapper;
    }

}
