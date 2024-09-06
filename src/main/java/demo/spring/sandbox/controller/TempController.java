package demo.spring.sandbox.controller;

import demo.spring.sandbox.client.TemperatureClient;
import demo.spring.sandbox.model.User;
import demo.spring.sandbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/convert")
public class TempController {

    @Autowired
    private TemperatureClient temperatureClient;

    @GetMapping("/fahrenheit-to-celsius")
    public String fahrenheitToCelsius(@RequestParam String fahrenheit) {
        return temperatureClient.fahrenheitToCelsius(fahrenheit);
    }

    @GetMapping("/celsius-to-fahrenheit")
    public String celsiusToFahrenheit(@RequestParam String celsius) {
        return temperatureClient.celsiusToFahrenheit(celsius);
    }
}
