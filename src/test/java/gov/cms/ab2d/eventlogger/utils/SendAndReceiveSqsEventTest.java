package gov.cms.ab2d.eventlogger.utils;//package gov.cms.ab2d.eventlogger.utils;
//
//import com.amazonaws.services.sqs.AmazonSQS;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import gov.cms.ab2d.eventlogger.LogManager;
//import gov.cms.ab2d.eventlogger.LoggableEvent;
//import gov.cms.ab2d.eventlogger.SpringBootApp;
//import gov.cms.ab2d.eventlogger.api.EventsListener;
//import gov.cms.ab2d.eventlogger.events.ApiRequestEvent;
//import gov.cms.ab2d.eventlogger.events.ApiResponseEvent;
//import java.util.List;
//import org.junit.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//
//import static gov.cms.ab2d.eventlogger.utils.SQSConfig.EVENTS_QUEUE;
//import static org.mockito.Mockito.timeout;
//import static org.mockito.Mockito.verify;
//
//@SpringBootTest(classes = SpringBootApp.class)
//@Testcontainers
//public class SendAndReceiveSqsEventTest {
//
//    static {
//        System.setProperty("spring.liquibase.enabled", "false");
//        System.setProperty("sqs.enabled", "true");
//    }
//
//    @Container
//    private static final AB2DLocalstackContainer localstackContainer = new AB2DLocalstackContainer();
//
//    @Autowired
//    SendSQSEvent sendSQSEvent;
//
//    @Autowired
//    private AmazonSQS amazonSQS;
//
//    @MockBean
//    LogManager logManager;
//
//    @Autowired
//    EventsListener eventsListener;
//
//    @Before
//    public void initMocks() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//
//    @Test
//    void testQueueUrl() {
//        String url = amazonSQS.getQueueUrl(EVENTS_QUEUE).getQueueUrl();
//        Assertions.assertTrue(url.contains("localhost:"));
//        Assertions.assertTrue(url.contains(EVENTS_QUEUE));
//    }
//
//    @Test
//    void testSendAndReceiveMessages() throws JsonProcessingException {
//        final ArgumentCaptor<LoggableEvent> captor = ArgumentCaptor.forClass(LoggableEvent.class);
//        ApiRequestEvent sentApiRequestEvent = new ApiRequestEvent("organization", "jobId", "url", "ipAddress", "token", "requestId");
//        ApiResponseEvent sentApiResponseEvent = new ApiResponseEvent("organization", "jobId", HttpStatus.I_AM_A_TEAPOT, "ipAddress", "token", "requestId");
//
//        sendSQSEvent.send(sentApiRequestEvent);
//        sendSQSEvent.send(sentApiResponseEvent);
//
//        //timeout needed because the sqs listener (that uses logManager) is a separate process.
//        verify(logManager, timeout(1000).times(2)).log(captor.capture());
//
//        List<LoggableEvent> loggedApiRequestEvent = captor.getAllValues();
//        Assertions.assertEquals(sentApiRequestEvent, loggedApiRequestEvent.get(0));
//        Assertions.assertEquals(sentApiResponseEvent, loggedApiRequestEvent.get(1));
//        Assertions.assertEquals(ApiRequestEvent.class, loggedApiRequestEvent.get(0).getClass());
//        Assertions.assertEquals(ApiResponseEvent.class, loggedApiRequestEvent.get(1).getClass());
//    }
//}
