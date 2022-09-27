package gov.cms.ab2d.eventlogger.api;

import gov.cms.ab2d.eventclient.messages.GeneralSQSMessage;
import gov.cms.ab2d.eventclient.messages.SQSMessages;
import gov.cms.ab2d.eventlogger.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@DependsOn({"SQSConfig"})
public class EventsListener {
    private LogManager logManager;

    public EventsListener(LogManager logManager) {
        this.logManager = logManager;
    }

    @SqsListener(value = "${sqs.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processEvents(SQSMessages sqsMessage) {
        log.info("EventsListener: Processing events from SQS: " + sqsMessage);
        switch (sqsMessage.getClass().getSimpleName()) {
            case "GeneralSQSMessage":
                logManager.log(((GeneralSQSMessage) sqsMessage).getLoggableEvent());
                break;
            default:
                log.info("Can't Identify Message");
        }
    }
}
