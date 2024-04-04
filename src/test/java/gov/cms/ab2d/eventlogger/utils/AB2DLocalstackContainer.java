package gov.cms.ab2d.eventlogger.utils;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class AB2DLocalstackContainer extends LocalStackContainer {

    private static final DockerImageName IMAGE_VERSION = DockerImageName.parse("localstack/localstack:latest");

    public AB2DLocalstackContainer() {
        super(IMAGE_VERSION);
    }

    @Override
    public void start() {
        System.setProperty("cloud.aws.stack.auto", "false");
        System.setProperty("cloud.aws.region.static", "us-east-1");
        System.setProperty("com.amazonaws.sdk.disableCertChecking", "");
        super.withServices(Service.SQS);
        super.start();
        System.setProperty("AWS_SQS_URL",
                "localhost:" + this.getMappedPort(EnabledService.named("SQS").getPort()));
                
        // Insert a delay after starting the container
        try {
            // Wait for a specified time (e.g., 60000 milliseconds equals 1 minute)
            log.info("Waiting for manual verification..."); // Log message indicating wait start
            Thread.sleep(90000); // Adjust the time as necessary
            log.info("Wait completed. Resuming execution..."); // Log message indicating wait end
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted during wait", e);
        }
    }
}
