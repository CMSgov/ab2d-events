package gov.cms.ab2d.eventlogger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(
        scanBasePackages = {"gov.cms.ab2d.eventlogger", "gov.cms.ab2d.eventclient"}
)
public class SpringBootApp {
    public static void main(String[] args) {
        log.info("Starting Spring Boot Application Events-client");
        SpringApplication.run(SpringBootApp.class, args);
    }
}

