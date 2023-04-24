package com.cps_assignment.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Import(CorsConfig.class)
public class Controller {

    public static void main(String[] args) throws Exception { // Pilot
        SpringApplication.run(Controller.class, args);
            HTTPSniffer sniffer = new HTTPSniffer();
            sniffer.sendGETRequest("https://www.valutakurser.dk/");
    }

}
