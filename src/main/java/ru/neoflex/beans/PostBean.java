package ru.neoflex.beans;

import org.springframework.stereotype.Component;
import ru.neoflex.types.PostRequestType;
import ru.neoflex.types.ResponseType;

@Component
public class PostBean {

    public ResponseType response(PostRequestType input) {
        // We create a new object of the ResponseType
        // Camel will be able to serialise this automatically to JSON
        return new ResponseType("Thanks for your post, " + input.getName() + "!");
    }

}
