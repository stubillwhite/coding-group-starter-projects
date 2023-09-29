package org.starter.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.starter.domain.Ping;
import org.starter.domain.Pong;
import org.starter.testcommon.TestApplicationServer;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({TestApplicationServer.class})
public class PingResourceIntegrationTest {

    private static final TypeRef<Pong> PONG_TYPE = new TypeRef<Pong>() {
    };

    private static final String ENDPOINT = "http://localhost:8080/ping";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void pingThenReturnsPong() {
        // Given, When
        final Response response = RestAssured
                .given()
                .body(new Ping("message", 23))
                .contentType(ContentType.JSON)
                .post(ENDPOINT)
                .then()
                .extract().response();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        final Pong pong = response.getBody().as(PONG_TYPE);
        assertThat(pong.message()).isEqualTo("message");
        assertThat(pong.number()).isEqualTo(23);
    }
}