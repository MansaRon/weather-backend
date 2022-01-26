package co.za.demo;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import io.micronaut.http.client.*;
import io.micronaut.http.client.annotation.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {



}
