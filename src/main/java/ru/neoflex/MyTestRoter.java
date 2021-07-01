package ru.neoflex;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import ru.neoflex.types.PostRequestType;
import ru.neoflex.types.ResponseType;

@Component
public class MyTestRoter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
             .component("servlet")
             .bindingMode(RestBindingMode.auto);

        rest()
                .path("/ikanoTest")
                .consumes("application/json")
                .produces("application/json")

                .post()
                .type(PostRequestType.class) // Setting the request type enables Camel to unmarshal the request to a Java object
                .outType(ResponseType.class) // Setting the response type enables Camel to marshal the response to JSON
                .to("bean:postBean");

    }

    }
