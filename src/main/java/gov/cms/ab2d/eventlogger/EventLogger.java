package gov.cms.ab2d.eventlogger;

import gov.cms.ab2d.eventlibs.events.LoggableEvent;

public interface EventLogger {
    void log(LoggableEvent event);
}
