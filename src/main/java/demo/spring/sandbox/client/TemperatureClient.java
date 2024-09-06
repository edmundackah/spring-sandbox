package demo.spring.sandbox.client;

import demo.spring.sandbox.model.soap.temp.CelsiusToFahrenheit;
import demo.spring.sandbox.model.soap.temp.CelsiusToFahrenheitResponse;
import demo.spring.sandbox.model.soap.temp.FahrenheitToCelsius;
import demo.spring.sandbox.model.soap.temp.FahrenheitToCelsiusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.stereotype.Service;

@Service
public class TemperatureClient {

    @Autowired
    private WebServiceTemplate wsTemplate;

    public String fahrenheitToCelsius(String fahrenheit) {
        // Create request
        FahrenheitToCelsius request = new FahrenheitToCelsius();
        request.setFahrenheit(fahrenheit);

        // Send SOAP request and receive response
        FahrenheitToCelsiusResponse response = (FahrenheitToCelsiusResponse) wsTemplate
                .marshalSendAndReceive(request, new SoapActionCallback("https://www.w3schools.com/xml/FahrenheitToCelsius"));

        return response.getFahrenheitToCelsiusResult();
    }

    public String celsiusToFahrenheit(String celsius) {
        CelsiusToFahrenheit request = new CelsiusToFahrenheit();
        request.setCelsius(celsius);

        CelsiusToFahrenheitResponse response = (CelsiusToFahrenheitResponse) wsTemplate
                .marshalSendAndReceive(request, new SoapActionCallback("https://www.w3schools.com/xml/CelsiusToFahrenheit"));

        return response.getCelsiusToFahrenheitResult();
    }
}
