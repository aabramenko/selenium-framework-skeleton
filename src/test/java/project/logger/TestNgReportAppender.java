package project.logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;
import project.driverFactory.DriverFactory;

public class TestNgReportAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent event) {
        String log = this.layout.format(event);
        log = log.replaceAll("\n", "<br>\n");
        log = log.replaceAll("#THREAD#", "[" + DriverFactory.getCurrentThreadId() + "]");
        Reporter.log(log);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}