package gov.cms.ab2d.eventlogger.api;

import gov.cms.ab2d.eventclient.messages.AlertSQSMessage;
import gov.cms.ab2d.eventclient.messages.GeneralSQSMessage;
import gov.cms.ab2d.eventclient.messages.KinesisSQSMessage;
import gov.cms.ab2d.eventclient.messages.LogAndTraceSQSMessage;
import gov.cms.ab2d.eventclient.messages.SQSMessages;
import gov.cms.ab2d.eventclient.messages.SlackSQSMessage;
import gov.cms.ab2d.eventclient.messages.TraceAndAlertSQSMessage;
import gov.cms.ab2d.eventclient.messages.TraceSQSMessage;
import gov.cms.ab2d.eventlogger.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventsListener {
    private final LogManager logManager;

    public EventsListener(LogManager logManager) {
        this.logManager = logManager;
    }

    @SqsListener(value = "ab2d-events", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processEvents(SQSMessages sqsMessage) {
        log.info("EventsListener: Processing events from SQS: " + sqsMessage);
        switch (sqsMessage.getClass().getSimpleName()) {
            case "GeneralSQSMessage" ->
                    logManager.log(((GeneralSQSMessage) sqsMessage).getLoggableEvent());
            case "AlertSQSMessage" ->
                    logManager.alert(((AlertSQSMessage) sqsMessage).getMessage(), ((AlertSQSMessage) sqsMessage).getEnvironments());
            case "TraceSQSMessage" ->
                    logManager.trace(((TraceSQSMessage) sqsMessage).getMessage(), ((TraceSQSMessage) sqsMessage).getEnvironments());
            case "TraceAndAlertSQSMessage" ->
                    logManager.logAndAlert(((TraceAndAlertSQSMessage) sqsMessage).getLoggableEvent(), ((TraceAndAlertSQSMessage) sqsMessage).getEnvironments());
            case "LogAndTraceSQSMessage" ->
                    logManager.logAndTrace(((LogAndTraceSQSMessage) sqsMessage).getLoggableEvent(), ((LogAndTraceSQSMessage) sqsMessage).getEnvironments());
            case "SlackSQSMessage" ->
                    logManager.log(LogManager.LogType.SQL, ((SlackSQSMessage) sqsMessage).getLoggableEvent());
            case "KinesisSQSMessage" ->
                    logManager.log(LogManager.LogType.KINESIS, ((KinesisSQSMessage) sqsMessage).getLoggableEvent());
            default -> log.info("Can't Identify Message " + sqsMessage);
        }
    }
}
