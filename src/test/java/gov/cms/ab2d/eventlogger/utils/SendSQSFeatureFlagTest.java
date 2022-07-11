package gov.cms.ab2d.eventlogger.utils;

import com.amazonaws.services.sqs.AmazonSQS;
import gov.cms.ab2d.eventclient.clients.SQSEventClient;
import gov.cms.ab2d.eventclient.events.ApiRequestEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
public class SendSQSFeatureFlagTest {

    static {
        System.setProperty("spring.liquibase.enabled", "false");
    }

    @Container
    private static final AB2DLocalstackContainer LOCALSTACK_CONTAINER = new AB2DLocalstackContainer();

    @Autowired
    private SQSEventClient sendSQSEvent;

    @SpyBean
    private AmazonSQS amazonSQS;

    @Test
    void testSendAndReceiveMessages() {
        ApiRequestEvent sentApiRequestEvent = new ApiRequestEvent("organization", "jobId", "url", "ipAddress", "token", "requestId");

        sendSQSEvent.sendLogs(sentApiRequestEvent);

        verify(amazonSQS, never()).getQueueUrl(anyString());
    }
}
