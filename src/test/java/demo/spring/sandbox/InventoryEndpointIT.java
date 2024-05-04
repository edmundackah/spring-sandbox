package demo.spring.sandbox;

import demo.spring.sandbox.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class InventoryEndpointIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        String baseURI = StringUtils.join("http://localhost:", port);

        RestAssured.baseURI = baseURI;
    }

    @Test
    public void GivenValidInfo_ShouldBeInStock() {
        final String SKU_CODE = "iphone_15";
        final Integer QUANTITY = 2;

        InventoryClientStub.stubInventoryCall(SKU_CODE, QUANTITY);

        String baseURI = StringUtils.join("http://localhost:", port);

        RestAssured.baseURI = baseURI;

        given()
                .pathParam("skuCode", SKU_CODE)
                .when()
                .get("/api/stock/{skuCode}")
                .then()
                .statusCode(200);
    }

    @Test
    public void GivenForbiddenUser_ShouldFail() {

        final String SKU_CODE = "turbo";
        final Integer QUANTITY = 2;

        InventoryClientStub.stubForbiddenCall(SKU_CODE, QUANTITY);

        given()
                .pathParam("skuCode", SKU_CODE)
                .when()
                .get("/api/stock/{skuCode}")
                .then()
                .statusCode(500)
                .body("error", equalTo("Internal Server Error"))
                .body("path", equalTo("/api/stock/turbo"))
                .body("timestamp", notNullValue())
                .body("status", equalTo(500));
    }
}


