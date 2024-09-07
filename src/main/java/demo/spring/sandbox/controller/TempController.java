package demo.spring.sandbox.controller;

import demo.spring.sandbox.client.TemperatureClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
