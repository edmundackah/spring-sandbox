package demo.spring.sandbox.client;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryClient {

    private final String url;

    @Autowired
    public InventoryClient(@Value("${inventory.url}") String url) {
        this.url = url;
    }

    public Boolean isInStock(String skuCode) {
        String endpoint = "/item/" + skuCode;

        try {
            HttpResponse<String> response = Unirest.get(url + endpoint)
                    .header("Content-Type", "application/json")
                    .asString();

            if (response.getStatus() == 200) {
                log.debug("Response body: {}", response.getBody());
                return true;
            } else {
                log.debug("Request failed. Status code: {}", response.getStatus());
            }
        } catch (UnirestException e) {
            log.debug("Error calling the inventory endpoint: {}", e.getMessage());
        }

        return false;
    }
}
