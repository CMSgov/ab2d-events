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
        
        System.out.println("STARTING AB2DLOCALSTACK CONTAINER...");
        System.out.println("CONTAINER ENDPOINT URI = " + this.getEndpoint().toString());
        System.out.println("SQS SERVICE PORT = " + String.valueOf(this.getMappedPort(EnabledService.named("SQS").getPort())));

        System.setProperty("AWS_URL", "localhost:" + this.getMappedPort(EnabledService.named("SQS").getPort()));
        System.setProperty("HOSTNAME_EXTERNAL", this.getEndpoint().toString());
        System.setProperty("SQS_ENDPOINT_STRATEGY", "off");

        String aws_url = System.getProperty("AWS_URL");
        String hostname_external = System.getProperty("HOSTNAME_EXTERNAL");
        String sqs_endpoint_strategy = System.getProperty("SQS_ENDPOINT_STRATEGY");
        
        System.out.println("Container ENV Vars: ");
        System.out.println("AWS_URL = " + aws_url);
        System.out.println("HOSTNAME_EXTERNAL = " + hostname_external);
        System.out.println("SQS_ENDPOINT_STRATEGY = " + sqs_endpoint_strategy);

    }
}
