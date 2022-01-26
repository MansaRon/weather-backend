package co.za.demo.business.base;


import co.za.demo.mapper.ObjectMapper;

import co.za.demo.repository.Repository;
import com.google.gson.Gson;
import jakarta.inject.Inject;



public abstract class AbstractService {

    @Inject
    protected ObjectMapper mapper;

    @Inject
    protected Repository store;

    protected <T> T map(Class<T> classz, String json) {
        System.out.println(json);
        return new Gson().fromJson(json, classz);
    }


}
