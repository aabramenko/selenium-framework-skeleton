package project.logger;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;
import project.core.Utils;

public class FileAppender extends DailyRollingFileAppender {

    @Override
    public void append(LoggingEvent event) {
        String log = "[" + Utils.getCurrentThreadId() + "]: " + event.getMessage();

        LoggingEvent modifiedEvent = new LoggingEvent(event.getFQNOfLoggerClass(), event.getLogger(), event.getTimeStamp(), event.getLevel(), log,
                event.getThreadName(), event.getThrowableInformation(), event.getNDC(), event.getLocationInformation(),
                event.getProperties());

        super.append(modifiedEvent);
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

}
