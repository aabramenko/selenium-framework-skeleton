package project.logger;

import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import project.core.Dictionary;
import project.core.RunTimeDataStorage;
import project.core.TestRunParams;
import project.core.Utils;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Logger {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("");

    static void logPageSource(ITestResult iTestResult, WebDriver driver) {

        String caseStatus = Dictionary.FAILED;
        if (iTestResult.isSuccess()) {
            caseStatus = Dictionary.PASSED;
        }

        String fileName = "source-" + caseStatus + "_" + getSuiteName(iTestResult) + "_" +
                        getCaseName(iTestResult) + "_" + Utils.getUniqueString() + ".txt";

        String filePath = TestRunParams.getPathToCurrentArtifactsFolder() + fileName;

        String source = Utils.getPageSource(driver);

        try {
            Utils.createFile(filePath, source);
            log.info("page source is saved here: " + filePath);
        }
        catch (IOException e) {
            log.info(e);
        }

        // allure
        putStringToAllure("page source is saved here: " + filePath);

        // reportNg
        Reporter.log("<br><hr align='left' width='800' size='2' color='#BEBEBE' /><br>"
                + "<font color='blue'><b>page source:</b></font> <a href='" + filePath + "' target='_blank'>"
                + filePath + "</a><br><br>"
                + "<hr align='left' width='800' size='2' color='#BEBEBE' /><br>");
    }

    private static String getSuiteName(ITestResult iTestResult) {
        return Utils.removePackageNameFromPath(iTestResult.getInstanceName());
    }

    private static String getCaseName(ITestResult iTestResult) {
        return iTestResult.getMethod().getMethodName();
    }

    static void logScreenshot(ITestResult iTestResult, WebDriver driver) {
        Screenshot screenshot = Utils.makeScreenshot(driver);
        String caseStatus = Dictionary.FAILED;
        if (iTestResult.isSuccess()) {
            caseStatus = Dictionary.PASSED;
        }

        String fileName = "screen-" + caseStatus + "-" + getSuiteName(iTestResult) + "-"
                + getCaseName(iTestResult) + "-" + Utils.getUniqueString() + ".png";

        Utils.saveScreenshotAsFile(fileName, screenshot);

        // allure
        putScreenshotToAllure(screenshot);

        // reportNg
        putScreenshotToReportNg(fileName);
    }

    public static void logCurrentUrl(WebDriver driver) {
        String url = driver.getCurrentUrl();

        log.info("page url: " + url);

        // allure
        putStringToAllure("page url: " + url);
    }

    public static void printTestInfoHeader(ITestResult iTestResult) {
        Utils.printDoubleLine();
        log.info("* test case [" + getCaseName(iTestResult) + "] started | # " + RunTimeDataStorage.RunningTestStatistic.getTestOrderNumber());
        log.info("* suite [" + getSuiteName(iTestResult) + "]");
        Utils.printDoubleLine();
    }

    public static void printTestInfoBottom(ITestResult iTestResult) {
        String caseStatus = Dictionary.FAILED;
        if (iTestResult.isSuccess()) {
            caseStatus = Dictionary.PASSED;
        }
        Utils.printDoubleLine();
        log.info("* test case [" + getCaseName(iTestResult) + "] finished");
        log.info("* [ " + caseStatus.toUpperCase() + " ]");
        log.info("* suite [" + getSuiteName(iTestResult) + "]");
        Utils.printDoubleLine();
        log.info("");
        log.info("");
    }

    @Attachment(value = "info")
    private static String putStringToAllure(String string) {
        return string;
    }

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] putScreenshotToAllure(Screenshot screenshot) {
        final BufferedImage image = screenshot.getImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write( image, "jpg", baos );
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imageInByte = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageInByte;
    }

    private static void putScreenshotToReportNg(String fileName) {

        String relativeFilePath = "../../" +
                TestRunParams.getNameOfFolderWithAllTestRunArtifacts() +
                TestRunParams.getNameOfCurrentRunLogFolder() +
                fileName;

        Reporter.log(
                "<br><br><hr align='left' width='800' size='2' color='#BEBEBE' /><br>"
                        + "<a target='_blank' href='"
                        + relativeFilePath + "'><img src='" + relativeFilePath + "' width='800'></a><br><br>"
                        + "<hr align='left' width='800' size='2' color='#BEBEBE' /><br>"
        );

    }

}
