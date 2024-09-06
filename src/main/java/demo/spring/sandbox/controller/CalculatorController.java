package demo.spring.sandbox.controller;

import demo.spring.sandbox.client.CalculatorClient;
import demo.spring.sandbox.model.soap.calc.Add;
import demo.spring.sandbox.model.soap.calc.Subtract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calc")
public class CalculatorController {

    @Autowired
    private CalculatorClient calculatorClient;

    @PostMapping("/add")
    public Integer add(@RequestBody Add add) {
        return calculatorClient.add(add);
    }

    @PostMapping("/subtract")
    public Integer subtract(@RequestBody Subtract subtract) {
        return calculatorClient.subtract(subtract);
    }
}