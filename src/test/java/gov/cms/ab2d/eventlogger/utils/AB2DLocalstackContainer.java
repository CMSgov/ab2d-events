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
        
        System.out.println("STARTING AB2DLOCALSTACK CONTAINER. SQS SERVICE PORT = ");
        System.out.println(String.valueOf(this.getMappedPort(EnabledService.named("SQS").getPort())));

        System.setProperty("AWS_URL", "localhost:" + this.getMappedPort(EnabledService.named("SQS").getPort()));
        // System.setProperty("AWS_SQS_URL", "localhost:4566");

        String property = System.getProperty("AWS_URL");
        System.out.println("AWS_URL PROPERTY = " + property);
    }
}
