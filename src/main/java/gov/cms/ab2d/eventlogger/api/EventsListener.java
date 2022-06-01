package gov.cms.ab2d.eventlogger.api;

import gov.cms.ab2d.eventlogger.LogManager;
import gov.cms.ab2d.eventlogger.LoggableEvent;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
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

    @SqsListener(value = "ab2d-events", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void processEvents(LoggableEvent loggableEvent, @NotNull Acknowledgment ack) {
        log.info("EventsListener: Processing events from SQS: " + loggableEvent);
        logManager.log(loggableEvent);
        ack.acknowledge();
    }
}
