package it.sevenbits.hwspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of an application
 */
@SpringBootApplication
public class Application {

    /**
     * Start of an application
     *
     * @param args can contain config or input data
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
