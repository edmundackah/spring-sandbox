package demo.spring.sandbox.controller;

import demo.spring.sandbox.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class InventoryController {

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private final InventoryService inventoryService;

    @GetMapping(path = "/stock/{skuCode}" , produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> stockCheck(@PathVariable(name = "skuCode" ) String skuCode) {
        System.out.println("looking for sku " + skuCode);
        String response = inventoryService.stockCheck(skuCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/marco" , produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> polo() {
        log.debug("I said marco");
        return ResponseEntity.ok("polo");
    }
}