package project.logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import project.driverFactory.DriverFactory;

public class ConsoleAppender extends AppenderSkeleton {
    @Override
    protected void append(LoggingEvent event) {
        String log = this.layout.format(event);
        log = log.replaceAll("#THREAD#", "[" + DriverFactory.getCurrentThreadId() + "]");
        System.out.print(log);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
