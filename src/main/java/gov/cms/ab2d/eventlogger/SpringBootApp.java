package gov.cms.ab2d.eventlogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"gov.cms.ab2d.eventlogger", "gov.cms.ab2d.eventclient"},
        exclude = {
            io.awspring.cloud.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
            io.awspring.cloud.autoconfigure.context.ContextStackAutoConfiguration.class,
            io.awspring.cloud.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
        }
)
public class SpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}

