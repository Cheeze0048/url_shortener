/*
package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class UrlShortenerResourceTest {

    @Test
    public void testCreateShortUrl() {
        String requestBody = """
            {
                "url": "https://www.example.com/very-long-url",
                "description": "Test URL"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(201)
                .body("originalUrl", equalTo("https://www.example.com/very-long-url"))
                .body("description", equalTo("Test URL"))
                .body("shortCode", notNullValue())
                .body("shortUrl", containsString("/s/"))
                .body("clickCount", equalTo(0))
                .body("active", equalTo(true));
    }

    @Test
    public void testCreateShortUrlWithCustomCode() {
        String requestBody = """
            {
                "url": "https://www.example.com/custom",
                "description": "Custom code test",
                "customCode": "custom1"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(201)
                .body("shortCode", equalTo("custom1"));
    }

    @Test
    public void testInvalidUrlFormat() {
        String requestBody = """
            {
                "url": "not-a-valid-url",
                "description": "Invalid URL"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetUrlDetails() {
        // First create a URL
        String requestBody = """
            {
                "url": "https://www.example.com/details-test",
                "customCode": "detail1"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(201);

        // Then get its details
        given()
                .when()
                .get("/api/v1/urls/detail1")
                .then()
                .statusCode(200)
                .body("shortCode", equalTo("detail1"))
                .body("originalUrl", equalTo("https://www.example.com/details-test"));
    }

    @Test
    public void testUrlNotFound() {
        given()
                .when()
                .get("/api/v1/urls/nonexistent")
                .then()
                .statusCode(404);
    }

    @Test
    public void testRedirect() {
        // Create a URL first
        String requestBody = """
            {
                "url": "https://www.example.com/redirect-test",
                "customCode": "redir1"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(201);

        // Test redirect
        given()
                .redirects().follow(false)
                .when()
                .get("/s/redir1")
                .then()
                .statusCode(307)
                .header("Location", equalTo("https://www.example.com/redirect-test"));
    }

    @Test
    public void testUpdateUrl() {
        // Create a URL first
        String createBody = """
            {
                "url": "https://www.example.com/update-test",
                "customCode": "update1"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(createBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(201);

        // Update it
        String updateBody = """
            {
                "description": "Updated description",
                "active": false
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/api/v1/urls/update1")
                .then()
                .statusCode(200)
                .body("description", equalTo("Updated description"))
                .body("active", equalTo(false));
    }

    @Test
    public void testDeleteUrl() {
        // Create a URL first
        String requestBody = """
            {
                "url": "https://www.example.com/delete-test",
                "customCode": "delete1"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(201);

        // Delete it
        given()
                .when()
                .delete("/api/v1/urls/delete1")
                .then()
                .statusCode(204);

        // Verify it's gone
        given()
                .when()
                .get("/api/v1/urls/delete1")
                .then()
                .statusCode(404);
    }

    @Test
    public void testListUrls() {
        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/api/v1/urls")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }
}
*/
