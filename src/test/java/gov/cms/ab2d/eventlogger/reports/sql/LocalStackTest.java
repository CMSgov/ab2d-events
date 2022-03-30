//package gov.cms.ab2d.eventlogger.reports.sql;
//
//import com.amazonaws.services.sqs.AmazonSQS;
//import com.amazonaws.services.sqs.AmazonSQSClient;
//import com.amazonaws.services.sqs.model.Message;
//import com.amazonaws.services.sqs.model.SendMessageRequest;
//import gov.cms.ab2d.eventlogger.SpringBootApp;
//import gov.cms.ab2d.eventlogger.eventloggers.sql.SqlEventLogger;
//import java.util.List;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.testcontainers.containers.localstack.LocalStackContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
//@SpringBootTest(classes = SpringBootApp.class)
//@Testcontainers
//public class LocalStackTest {
//
//    public static final int ONE_MillSEC_IN_NANO = 1000000;
//
//    @Autowired
//    private LoggerEventSummary loggerEventSummary;
//
//    @Autowired
//    private SqlEventLogger logger;
//
//    static DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.12.18");
//
//
//    @ClassRule
//    public static LocalStackContainer localstack = new LocalStackContainer(localstackImage)
//            .withServices(LocalStackContainer.Service.SQS);
//    @Test
//    public void testQueueCreated() {
//        final AmazonSQS sqs = AmazonSQSClient
//                .builder()
//                .withEndpointConfiguration(localstack.getEndpointConfiguration(LocalStackContainer.Service.SQS))
//                .withCredentials(localstack.getDefaultCredentialsProvider()).build();
//        String mainQueueURL = sqs.createQueue("Main").getQueueUrl();
//
//        SendMessageRequest send_msg_request = new SendMessageRequest()
//                .withQueueUrl(mainQueueURL)
//                .withMessageBody("hello world");
//
//        assertTrue(sqs.listQueues().getQueueUrls().contains(mainQueueURL));
//
//        sqs.sendMessage(send_msg_request);
//        List<Message> messages = sqs.receiveMessage(mainQueueURL).getMessages();
//
//        assertEquals(1, messages.size());
//        assertEquals(messages.get(0).getBody(), "hello world");
//
//        for (Message m : messages) {
//            sqs.deleteMessage(mainQueueURL, m.getReceiptHandle());
//        }
//
//    }
//
////    @Test
////    public void getSummaryBasic() {
////        String jobId = "Job1";
////        String usr = "USER";
////        OffsetDateTime firstTime = OffsetDateTime.now().minusDays(11);
////        File file1 = new File("./file1");
////
////        LoggableEvent e1 = new JobStatusChangeEvent(usr, jobId, null, "SUBMITTED", "Job Created");
////        e1.setTimeOfEvent(firstTime);
////        logger.log(e1);
////        LoggableEvent e2 = new JobStatusChangeEvent(usr, jobId, "SUBMITTED", "IN_PROGRESS", "Job Started");
////        e2.setTimeOfEvent(firstTime.plusDays(1));
////        logger.log(e2);
////        LoggableEvent e3 = new FileEvent(usr, jobId, file1, FileEvent.FileStatus.OPEN);
////        e3.setTimeOfEvent(firstTime.plusDays(2));
////        logger.log(e3);
////        LoggableEvent e4 = new FileEvent(usr, jobId, file1, FileEvent.FileStatus.OPEN);
////        e4.setTimeOfEvent(firstTime.plusDays(3));
////        logger.log(e4);
////        LoggableEvent e5 = new ContractSearchEvent(usr, jobId, "CONTRACT1", 100, 90, 80, 2, 70, 1000, 2000, 1);
////        e5.setTimeOfEvent(firstTime.plusDays(4));
////        logger.log(e5);
////        LoggableEvent e6 = new FileEvent(usr, jobId, file1, FileEvent.FileStatus.CLOSE);
////        e6.setTimeOfEvent(firstTime.plusDays(5));
////        logger.log(e6);
////        LoggableEvent e7 = new ApiRequestEvent(usr, jobId, "/file", "1.1.1.1", "abc", "request1");
////        e7.setTimeOfEvent(firstTime.plusDays(6));
////        logger.log(e7);
////        LoggableEvent e8 = new JobStatusChangeEvent(usr, jobId, "IN_PROGRESS", "SUCCESSFUL", "Job Done");
////        e8.setTimeOfEvent(firstTime.plusDays(7));
////        logger.log(e8);
////        LoggableEvent e9 = new ApiResponseEvent(usr, jobId, HttpStatus.OK, "File Download", "", "request1");
////        e9.setTimeOfEvent(firstTime.plusDays(8));
////        logger.log(e9);
////        LoggableEvent e10 = new FileEvent(usr, jobId, file1, FileEvent.FileStatus.DELETE);
////        e10.setTimeOfEvent(firstTime.plusDays(9));
////        logger.log(e10);
////
////        JobSummaryEvent summary = loggerEventSummary.getSummary(jobId);
////        Assertions.assertEquals(jobId, summary.getJobId());
////        Assertions.assertEquals(usr, summary.getOrganization());
////
////        assertTrue(Math.abs(firstTime.getNano() - summary.getSubmittedTime().getNano()) < ONE_MillSEC_IN_NANO);
////        assertTrue(Math.abs(firstTime.plusDays(1).getNano() - summary.getInProgressTime().getNano()) < ONE_MillSEC_IN_NANO);
////        assertTrue(Math.abs(firstTime.plusDays(7).getNano() - summary.getSuccessfulTime().getNano()) < ONE_MillSEC_IN_NANO);
////        assertNull(summary.getCancelledTime());
////        assertNull(summary.getFailedTime());
////        Assertions.assertEquals(1, summary.getNumFilesCreated());
////        Assertions.assertEquals(1, summary.getNumFilesDeleted());
////        Assertions.assertEquals(1, summary.getNumFilesDownloaded());
////        Assertions.assertEquals(100, summary.getTotalNum());
////        Assertions.assertEquals(80, summary.getSuccessfullySearched());
////        Assertions.assertEquals(2, summary.getErrorSearched());
////    }
//}
