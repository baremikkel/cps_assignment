package com.cps_assignment.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSConnection { //Prøver at skabe en connection

   // @CrossOrigin(origins = "localhost:5500")
    @GetMapping("/data")
    public MyData getData() {
        MyData data = new MyData();
        DataStripper stripper = DataStripper.getInstance();
        data.setMessage("Hello from Java!");
        return data;
    }

    public static class MyData {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
