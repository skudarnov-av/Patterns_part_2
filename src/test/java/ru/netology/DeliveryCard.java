package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DeliveryCard {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));

    private DeliveryCard(){

    }
    private static void sendRequest(Registration.RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when() //
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        return faker.name().name();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static class Registration {

        private Registration() {
        }
        public static RegistrationDto getUser(String status) {
            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }
        public static RegistrationDto getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            sendRequest(registeredUser = getUser(status));
            return registeredUser;
        }
        @Value
        public static class RegistrationDto {
            String login;
            String password;
            String status;
        }
    }

}
