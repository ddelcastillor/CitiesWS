package com.autentia.tutos.springboot;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
 
@SpringBootApplication
@ComponentScan("com.autentia.tutos")
public class Application {
     
    static final Logger logger = LogManager.getLogger(Application.class.getName());
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
