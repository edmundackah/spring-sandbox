package demo.spring.sandbox.service;

import demo.spring.sandbox.client.InventoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

    private final InventoryClient inventoryClient;

    @Autowired
    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    public String stockCheck(String skuCode) {
        Boolean isProductInStock = inventoryClient.isInStock(skuCode);
        if(!isProductInStock) {
            throw new RuntimeException("Product is out of stock for "+ skuCode);
        }
        return "Item is in stock";
    }
}
