package com.cps_assignment.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CorsConfig.class)
public class Controller {


    public static void main(String[] args) throws Exception { // Pilot
        String API_KEY = "2f68cc286ef546b2aa09fbac10e33503";
        SpringApplication.run(Controller.class, args);
            ReadLastTimestamp r = new ReadLastTimestamp();
    }
}
