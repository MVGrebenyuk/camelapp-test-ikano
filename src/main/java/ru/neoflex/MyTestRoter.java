package ru.neoflex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.DefaultMessage;
import org.apache.camel.tooling.model.JsonMapper;
import org.apache.camel.util.json.JsonObject;
import org.springframework.stereotype.Component;
import ru.neoflex.types.PersonDTO;

@Component
public class MyTestRoter extends RouteBuilder {

    @Override
    public void configure() throws Exception {



        restConfiguration()
             .component("servlet")
             .bindingMode(RestBindingMode.auto);


        from("rest:post:ikanoTest?consumer=application/json")
                .routeId("Convert JSON to XML")
                .unmarshal().json(PersonDTO.class, PersonDTO.class)
                //.to("log:?showBody=true")
                .choice()
                .when(exchange -> (((PersonDTO) exchange.getIn().getBody()).getType().equals("developer")))
                .process(exchange -> {
                    changeExchange(exchange);
                })
                .to("file:src/data/persons/developers")
                .otherwise()
                .process(exchange -> {
                    changeExchange(exchange);
                })
                .to("file:src/data/persons/otherPersons");



    }

    private void changeExchange(Exchange exchange) throws JsonProcessingException {
        PersonDTO person = (PersonDTO) exchange.getIn().getBody();
        XmlMapper mapper = new XmlMapper();
        String xmlStr = mapper.writeValueAsString(person);
        System.out.println("PRINT MAP: " + xmlStr);
        DefaultMessage message = new DefaultMessage(exchange);
        message.setBody(xmlStr);
        message.setMessageId(person.getName());
        exchange.setMessage(message);
    }

}
