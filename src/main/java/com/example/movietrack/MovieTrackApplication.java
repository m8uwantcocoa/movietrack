package com.example.movietrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.movietrack", "service"})
public class MovieTrackApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieTrackApplication.class, args);
        System.out.println("Server Ready");
    }
    @Controller
    public class CustomErrorController implements ErrorController {
        @RequestMapping("/error")
        public String handleError() {
            return "404";
        }
    }
}
