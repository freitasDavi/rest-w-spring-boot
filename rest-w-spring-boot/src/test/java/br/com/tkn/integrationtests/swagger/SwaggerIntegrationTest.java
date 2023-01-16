package br.com.tkn.integrationtests.swagger;

import br.com.tkn.configs.TestConfigs;
import br.com.tkn.integrationtests.testcontainers.AbstractIntegrationTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldDisplaySwaggerUiPage() {
        var content = given()
                                .basePath("swagger-ui/index.html")
                                .port(TestConfigs.SERVER_PORT)
                                .when()
                                    .get()
                                .then()
                                    .statusCode(200)
                                .extract()
                                    .body()
                                        .asString();

        assertTrue(content.contains("Swagger UI"));
    }

}
