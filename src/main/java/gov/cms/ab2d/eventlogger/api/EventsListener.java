package gov.cms.ab2d.eventlogger.api;

import com.amazonaws.services.dynamodbv2.xspec.B;
import gov.cms.ab2d.eventclient.messages.GeneralSQSMessage;
import gov.cms.ab2d.eventclient.messages.SQSMessages;
import gov.cms.ab2d.eventlogger.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Unknown;
import org.checkerframework.checker.units.qual.C;
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
    public void processEvents(SQSMessages sqsMessage) {
        log.info("EventsListener: Processing events from SQS: " + sqsMessage);
        if (GeneralSQSMessage.class.equals(sqsMessage.getClass())) {
            logManager.log(((GeneralSQSMessage) sqsMessage).getLoggableEvent());
        } else {//should never hit
            log.info("Can't Identify Message");
        }
    }
}
