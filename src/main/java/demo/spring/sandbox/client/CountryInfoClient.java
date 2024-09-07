package demo.spring.sandbox.client;

import demo.spring.sandbox.model.soap.country.ListOfContinentsByCode;
import demo.spring.sandbox.model.soap.country.ListOfContinentsByCodeResponse;
import demo.spring.sandbox.model.soap.country.ListOfCountryNamesByName;
import demo.spring.sandbox.model.soap.country.ListOfCountryNamesByNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class CountryInfoClient {

    @Autowired
    @Qualifier("countryWebServiceTemplate")
    private WebServiceTemplate wsTemplate;

    public ListOfCountryNamesByNameResponse getListOfCountries() {
        ListOfCountryNamesByName request  = new ListOfCountryNamesByName();

        return (ListOfCountryNamesByNameResponse) wsTemplate
                .marshalSendAndReceive(request, new SoapActionCallback("http://webservices.oorsprong.org/websamples.countryinfo/ListOfCountryNamesByName"));
    }

    public ListOfContinentsByCodeResponse getContinents() {
        ListOfContinentsByCode request = new ListOfContinentsByCode();

        return (ListOfContinentsByCodeResponse) wsTemplate
                .marshalSendAndReceive(request, new SoapActionCallback("http://webservices.oorsprong.org/websamples.countryinfo/ListOfContinentsByCode"));
    }
}
