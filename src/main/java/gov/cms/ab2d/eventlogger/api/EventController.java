package gov.cms.ab2d.eventlogger.api;

import gov.cms.ab2d.eventlibs.events.LoggableEvent;
import gov.cms.ab2d.eventlogger.LogManager;

import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller for the Fraud Check service
 */
@RestController
@Slf4j
@Validated
public class EventController {

    private LogManager logManager;

    @Autowired
    public EventController(LogManager logManager) {
        this.logManager = logManager;
    }

    @PostMapping(value = "/helloworld")
    public boolean checkTransaction(
            @RequestBody LoggableEvent loggableEvent,
            HttpServletResponse response) {
        logManager.log(loggableEvent);
        return true;
    }

}