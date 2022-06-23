package gov.cms.ab2d.eventlogger.utils;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import gov.cms.ab2d.eventclient.clients.SQSEventClient;
import gov.cms.ab2d.eventclient.events.ApiRequestEvent;
import gov.cms.ab2d.eventclient.events.ApiResponseEvent;
import gov.cms.ab2d.eventclient.events.LoggableEvent;
import gov.cms.ab2d.eventlogger.LogManager;


import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static gov.cms.ab2d.eventclient.clients.SQSConfig.EVENTS_QUEUE;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
public class SendAndReceiveSqsEventTest {

    static {
        System.setProperty("spring.liquibase.enabled", "false");
    }

    @Container
    private static final AB2DLocalstackContainer LOCALSTACK_CONTAINER = new AB2DLocalstackContainer();

    @Autowired
    private SQSEventClient sendSQSEvent;

    @Autowired
    private AmazonSQS amazonSQS;

    @MockBean
    private LogManager logManager;

    @Test
    void testQueueUrl() {
        String url = amazonSQS.getQueueUrl(EVENTS_QUEUE).getQueueUrl();
        Assertions.assertTrue(url.contains("localhost:"));
        Assertions.assertTrue(url.contains(EVENTS_QUEUE));
    }

    @Test
    void testSendAndReceiveMessages() throws JsonProcessingException {
        final ArgumentCaptor<LoggableEvent> captor = ArgumentCaptor.forClass(LoggableEvent.class);
        ApiRequestEvent sentApiRequestEvent = new ApiRequestEvent("organization", "jobId", "url", "ipAddress", "token", "requestId");
        ApiResponseEvent sentApiResponseEvent = new ApiResponseEvent("organization", "jobId", HttpStatus.I_AM_A_TEAPOT, "ipAddress", "token", "requestId");

        sendSQSEvent.send(sentApiRequestEvent);
        sendSQSEvent.send(sentApiResponseEvent);

        //timeout needed because the sqs listener (that uses logManager) is a separate process.
        verify(logManager, timeout(1000).times(2)).log(captor.capture());


        List<LoggableEvent> loggedApiRequestEvent = captor.getAllValues();
        Assertions.assertEquals(sentApiRequestEvent, loggedApiRequestEvent.get(0));
        Assertions.assertEquals(sentApiResponseEvent, loggedApiRequestEvent.get(1));
        Assertions.assertEquals(ApiRequestEvent.class, loggedApiRequestEvent.get(0).getClass());
        Assertions.assertEquals(ApiResponseEvent.class, loggedApiRequestEvent.get(1).getClass());
    }
}
