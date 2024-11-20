package edu.usb.argosinstrumentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class ArgosInstrumentationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArgosInstrumentationApplication.class, args);
    }

    @Bean
    public String applicationVersion() {
        return "1.0.0";
    }

    public String getApplicationName() {
        return "Argos Instrumentation";
    }
}
