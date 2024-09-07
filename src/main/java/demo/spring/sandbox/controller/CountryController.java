package demo.spring.sandbox.controller;

import demo.spring.sandbox.client.CountryInfoClient;
import demo.spring.sandbox.model.soap.country.ListOfContinentsByCodeResponse;
import demo.spring.sandbox.model.soap.country.ListOfCountryNamesByNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    private CountryInfoClient countryInfoClient;

    @GetMapping("/countries")
    public ListOfCountryNamesByNameResponse getCountries() {
        return countryInfoClient.getListOfCountries();
    }

    @GetMapping("/continents")
    public ListOfContinentsByCodeResponse getContinents() {
        return countryInfoClient.getContinents();
    }
}
