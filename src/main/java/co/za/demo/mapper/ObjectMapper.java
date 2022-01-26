package co.za.demo.mapper;

import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@Prototype
public class ObjectMapper {

    @Inject
    private ModelMapper mapper;

    public <T, S> T map(Class<T> target, S source) {
        return mapper.map(source, target);
    }

    public <T> T fromJson(String json, Class<T> classOfT)  {
        return mapper.map(json, classOfT);
    }


    public <T> T readValue(FileReader fileReader, Class<T> classOfT)  {
        return mapper.map(fileReader, classOfT);
    }


    public <T, S> List<T> map(Class<T> target, List<S> sources) {
        return sources.stream().map(s -> map(target, s)).collect(Collectors.toList());
    }
}
