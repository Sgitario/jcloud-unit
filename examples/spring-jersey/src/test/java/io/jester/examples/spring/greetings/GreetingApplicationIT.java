package io.jester.examples.spring.greetings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.jester.api.Jester;
import io.jester.api.Spring;

@Jester
@Spring(forceBuild = true)
public class GreetingApplicationIT {

    @Test
    public void testSpringApp() {
        given().get("/greeting").then().body(is("Hello!"));
    }
}
