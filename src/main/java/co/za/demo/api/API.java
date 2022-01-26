package co.za.demo.api;


import co.za.demo.mapper.ObjectMapper;
import jakarta.inject.Inject;


public abstract class API {

    @Inject
    protected ObjectMapper mapper;
}
