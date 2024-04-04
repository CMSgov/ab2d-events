package gov.cms.ab2d.eventlogger.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

public class AB2DLocalstackContainer extends LocalStackContainer {

    private static final Logger log = LoggerFactory.getLogger(AB2DLocalstackContainer.class);
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
        System.setProperty("AWS_SQS_URL", "localhost:" + this.getMappedPort(EnabledService.named("SQS").getPort()));

        try {
            log.info("Container started. Waiting for manual verification...");
            // Sleep for a specified time. For example, 60000 milliseconds equals 1 minute.
            System.out.println("AWS_SQS_URL: " + awsSqsUrl);
            Thread.sleep(60000);
            log.info("Wait completed. Resuming execution...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted during wait", e);
        }
    }
}
