package demo.spring.sandbox.client;

import demo.spring.sandbox.model.soap.calc.Add;
import demo.spring.sandbox.model.soap.calc.AddResponse;
import demo.spring.sandbox.model.soap.calc.Subtract;
import demo.spring.sandbox.model.soap.calc.SubtractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class CalculatorClient {

    @Autowired
    @Qualifier("calcWebServiceTemplate")
    private WebServiceTemplate wsTemplate;

    public Integer add(Add add) {
        AddResponse response = (AddResponse) wsTemplate
                .marshalSendAndReceive(add, new SoapActionCallback("http://tempuri.org/Add"));
        return response.getAddResult();
    }

    public Integer subtract(Subtract subtract) {
        SubtractResponse response = (SubtractResponse) wsTemplate
                .marshalSendAndReceive(subtract, new SoapActionCallback("http://tempuri.org/Subtract"));
        return response.getSubtractResult();
    }
}
