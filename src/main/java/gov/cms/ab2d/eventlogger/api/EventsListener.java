package gov.cms.ab2d.eventlogger.api;

import gov.cms.ab2d.eventclient.messages.GeneralSQSMessage;
import gov.cms.ab2d.eventclient.messages.SQSMessages;
import gov.cms.ab2d.eventlogger.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventsListener {
    private LogManager logManager;

    public EventsListener(LogManager logManager) {
        this.logManager = logManager;
    }

    @SqsListener(value = "ab2d-events", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processEvents(SQSMessages loggableEvent) {
        log.info("EventsListener: Processing events from SQS: " + loggableEvent);
        switch (loggableEvent.getClass().getSimpleName()) {
            case "GeneralSQSMessage":
                logManager.log(((GeneralSQSMessage) loggableEvent).getLoggableEvent());
                break;
            default:
                log.info("Can't Identify Message");
        }
    }
}
