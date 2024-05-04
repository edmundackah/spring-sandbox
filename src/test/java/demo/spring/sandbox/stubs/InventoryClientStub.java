package demo.spring.sandbox.stubs;

import lombok.extern.slf4j.Slf4j;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
public class InventoryClientStub {
    public static void stubInventoryCall(String skuCode, Integer quantity) {
        log.debug("Stubbing inventory call for skuCode: {} and quantity: {}", skuCode, quantity);

        stubFor(get(urlEqualTo("/item/" + skuCode))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":true}")));
    }

    public static void stubNoInventoryCall(String skuCode, Integer quantity) {
        log.debug("Stubbing inventory call for skuCode: {} and quantity: {}", skuCode, quantity);

        stubFor(get(urlEqualTo("/item/" + skuCode))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":false}")));
    }

    public static void stubForbiddenCall(String skuCode, Integer quantity) {
        log.debug("Stubbing inventory call for skuCode: {} and quantity: {}", skuCode, quantity);

        stubFor(get(urlEqualTo("/item/" + skuCode))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"User not authorised\"}")));
    }
}