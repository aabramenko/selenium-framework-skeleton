package selenium.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static selenium.core.TestRunParams.*;

public class Utils {

    private static Logger log = Logger.getLogger("");

    private static final String TEST_TEXT_FILE_NAME = "textFile.txt";

    private static final String TEST_PNG_FILE_NAME = "pngFile.png";

    public static String getCurrentTimeWithSec() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getUniqueString() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMHHmmss");
        return dateFormat.format(d) + getRandomNumber(999);
    }

    public static String getCurrentDate(String dateFormatTemplate) {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatTemplate);
        return dateFormat.format(d);
    }

    public static String getUniqueStringCharsOnly(int len) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String getUniqueStringNumbersOnly(int len) {
        String SALTCHARS = "0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String getUniqueStringFromPossibleChars(String possibleChars, int len) {
        String SALTCHARS = possibleChars;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String getCurrentDate_YYYY_MM_dd_UTC() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(d);
    }

    public static String getTimeStamp() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return "[" + dateFormat.format(d) + "]";
    }

    public static void createFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            log.info("create folder: " + folderPath);
            file.mkdir();
            log.info("folder created: " + folderPath);
        }
    }

    public static void createFile(String filePath, String content) throws IOException {
        log.info("create file: " + filePath);
        File file = new File(filePath);
        file.createNewFile();
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    public static String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public static String generateFileReturnName(String fileType, String stringInsideFileName) {
        String runTime = getUniqueString();
        String newFileName = runTime;
        String sampleFileName = "";

        if (fileType.equalsIgnoreCase(Dictionary.TXT)) {
            newFileName = stringInsideFileName + runTime + getRandomNumberString() + ".txt";
            sampleFileName = TEST_TEXT_FILE_NAME;
        }

        if (fileType.equalsIgnoreCase(Dictionary.PNG)) {
            newFileName = stringInsideFileName + runTime + getRandomNumberString() + ".png";
            sampleFileName = TEST_PNG_FILE_NAME;
        }

        InputStream oInStream = null;
        OutputStream oOutStream = null;

        try {
            oInStream = new FileInputStream(TestRunParams.getPathToSampleFilesFolder() + sampleFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            oOutStream = new FileOutputStream(TestRunParams.getPathToTestFilesFolder() + newFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] oBytes = new byte[1024];
        int nLength;
        BufferedInputStream oBuffInputStream = new BufferedInputStream( oInStream );
        try {
            while ((nLength = oBuffInputStream.read(oBytes)) > 0)
            {
                oOutStream.write(oBytes, 0, nLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oInStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("created file: " + newFileName);
        return newFileName;
    }

    public static void deleteTempFiles() {
        log.info("deleting temp files");
        deleteTempFilesByExt(".txt");
        deleteTempFilesByExt(".png");
    }

    private static void deleteTempFilesByExt(String ext) {
        GenericExtFilter filter = new GenericExtFilter(ext);
        File fileDir = new File(TestRunParams.getPathToTestFilesFolder());

        //list out all the file name with .txt extension
        String[] list = fileDir.list(filter);

        if (list.length == 0) return;

        File fileDelete;

        for (String file : list){
            String temp = new StringBuffer(TestRunParams.getPathToTestFilesFolder())
                    .append(File.separator)
                    .append(file).toString();
            fileDelete = new File(temp);
            fileDelete.delete();
        }
    }

    public static class GenericExtFilter implements FilenameFilter {

        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }

    private static void renameFile(String pathToFile, String oldFileName, String newFileName) {
        File file = new File(pathToFile + oldFileName);
        file.renameTo(new File(newFileName));
        log.info("file [" + oldFileName + "] is renamed to [" + newFileName + "]");
    }

    private static void deleteFileByPartialName(String namePart, String folderPath) {
        final File folder = new File(folderPath);
        for (File f : folder.listFiles()) {
            if (f.getName().contains(namePart)) {
                f.delete();
            }
        }
    }

    public static double getFileSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }

    private static boolean waitUntilFileIsDownloaded(String fileName) {
        final Logger log = Logger.getLogger("");
        int timeoutSec = 30;
        int refreshStepSec = 1;
        int spentTimeSec = 0;
        boolean status = true;

        File file = new File(getPathToDownloads() + fileName);

        log.info("waiting up to " + timeoutSec + " sec until file [" + fileName + "] is downloaded");
        while (!file.exists() && (spentTimeSec < timeoutSec)) {
            sleepMsec(refreshStepSec * 1000);
            spentTimeSec = spentTimeSec + refreshStepSec;
            log.info("it's been " + spentTimeSec + " seconds");
        }

        if (!file.exists()) {
            log.error("the file was not downloaded after " + timeoutSec + " seconds: " + fileName);
            status = false;
        }

        return status;
    }

    public static void sleepMsec(final long msec) {
        try {
            Thread.sleep(msec);
        } catch (final InterruptedException e) {
            //
        }
    }

    private static void wgetFile(String url) {
        final Logger log = Logger.getLogger("");
        String[] array = url.split("/");
        String fileName = array[array.length - 1];
        log.info("trying to download file [" + fileName + "] from [" + url + "]");
        String command = "wget -O " + getPathToDownloads() + fileName + " " + url;
        try {
            Runtime.getRuntime().exec( command ).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitUntilFileIsDownloaded(fileName);
    }

    public static String getRandomNumberString() {
        Random rand = new Random();
        return "" + rand.nextInt(1000);
    }

    public static String getRandomNumberString(int number) {
        Random rand = new Random();
        return "" + rand.nextInt(number);
    }

    public static int getRandomNumber(int number) {
        Random rand = new Random();
        return rand.nextInt(number);
    }

    public static String getTextFromFile(String filePath) {
        StringBuilder text = new StringBuilder();
        BufferedReader br  = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filePath));
            while ((sCurrentLine = br.readLine()) != null) {
                text.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return text.toString();
    }

    public static boolean doesFileContainText(String filePath, String text) {
        String fileText = getTextFromFile(filePath);
        return fileText.contains(text);
    }

    public static boolean isFileDownloaded(String fileName) {
        boolean isDownloaded = false;
        String downloadsPath = getPathToDownloads();
        File file = new File(downloadsPath + fileName);
        if (file.exists()) {
            isDownloaded = true;
        }
        return isDownloaded;
    }

    public static List<String> sortAlphaBet(List<String> list) {
        final Logger log = Logger.getLogger("");
        Collections.sort(list);
        log.info("sorted from A to Z list contains " + list.size() + " items");
        log.info("sorted list: first item is [" + list.get(0) +
                "]; last item is [" + list.get(list.size() - 1) + "]");
        return list;
    }

    public static String getStringOfCharacters(String pattern, int length) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            string.append(pattern);
        }
        return string.toString();
    }

    public static String getCurrentOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getText1000Symbols() {
        String sFilePath = getPathToSampleFilesFolder() + "text_1000_symbols.txt";
        return getTextFromFile(sFilePath);
    }

    public static String removePackages(String str) {
        String[] packages = str.split("\\.");
        return packages[packages.length - 1];
    }

    public static Screenshot makeScreenshot(WebDriver driver) {
        log.info("making screenshot");
        return new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);
    }

    public static void saveScreenshotAsFile(String fileName, Screenshot screenshot) {
        String filePath = TestRunParams.getScreenshotsLocation() + fileName;
        final BufferedImage image = screenshot.getImage();
        try {
            ImageIO.write(image, "PNG", new File(filePath));
            log.info("screenshot is saved here: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printLine() {
        String line = Utils.getStringOfCharacters("-", 70);
        log.info(line);
    }

    public static void printDoubleLine() {
        String line = Utils.getStringOfCharacters("=", 70);
        log.info(line);
    }

    public static void printDashedLine() {
        String line = Utils.getStringOfCharacters("- ", 35);
        log.info(line);
    }

    public static long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    private static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double round(String stringValue, int places) {
        stringValue = stringValue.replace(",", ".").trim();
        double value = Double.parseDouble(stringValue.trim());
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getDeltaPercent(double amount1, double amount2) {
        double delta = Math.abs( amount1 * 100 / amount2 - 100 );
        log.info("delta of " + amount1 + " and " + amount2 + " is " + delta + "%");
        return delta;
    }

    public static void printPageUploadTimeStatistics() {
        final Logger log = Logger.getLogger("");
        printDoubleLine();
        log.info("PAGE LOAD STATISTICS");
        printDoubleLine();
        log.info("|          PAGE           |  TIME, SEC  |   THREAD   |");
        printDoubleLine();
        ArrayList<String[]> array = RunTimeDataStorage.PagesLoadStatistics.getPageUploadingTimeArray();
        for (int i = 0; i <= array.size() - 1; i++) {
            log.info("| " +
                    array.get(i)[0] + " | " +
                    array.get(i)[2] + " sec | " +
                    array.get(i)[1] + " |");
            printLine();
        }
        printDoubleLine();
    }

    public static void createFilePageUploadTimeStatistics() {
        final Logger log = Logger.getLogger("");
        log.info("creating of csv statistics file");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(getPathToCurrentArtifactsFolder() + "/upload_pages_statistics.csv", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("Page,Time (sec),Thread");
        ArrayList<String[]> array = RunTimeDataStorage.PagesLoadStatistics.getPageUploadingTimeArray();
        for (int i = 0; i <= array.size() - 1; i++) {
            writer.println(array.get(i)[0] + "," + array.get(i)[2] + "," + array.get(i)[1]);
        }
        writer.close();
    }

    public static void savePageUploadTime(String page, Long start, Long finish) {
        final Logger log = Logger.getLogger("");
        String currentThread = "" + getCurrentThreadId();

        double timeSec = (finish - start)/1000.0;
        String timeSecStr = "" + timeSec;
        String plus = "";

        if (timeSec >= TestRunParams.getWaitForPageUploadSec()) {
            timeSecStr = "unknown";
        }
        RunTimeDataStorage.PagesLoadStatistics.setResult(
                page,
                currentThread,
                "" + timeSecStr + plus);
        log.info("[ LOAD STATISTIC: " +
                "PAGE = " + page +
                " | THREAD = " + currentThread +
                " | TIME = " + timeSecStr + " SEC" + plus + " ]");
    }

    public static void killProcessesTree(String rootNamePart) {
        try {
            List<String> browsersPids = getPIDsByNamePart(rootNamePart);
            Iterator var3 = browsersPids.iterator();

            while(var3.hasNext()) {
                String pid = (String)var3.next();
                killProcessByPid(pid);
            }

        } catch (IOException | InterruptedException var4) {
            log.warn("Cannot kill process with name '" + rootNamePart + "'");
        }
    }

    private static void killProcessByPid(String pid) throws IOException, InterruptedException {
        Process process = (new ProcessBuilder(new String[]{"kill", "-9", pid})).start();
        process.waitFor();
    }

    private static List<String> getPIDsByNamePart(String value) throws IOException, InterruptedException {
        Process process = (new ProcessBuilder(new String[]{"pgrep", value})).start();
        process.waitFor();
        return inputStreamToList(process.getInputStream());
    }

    public static List<String> inputStreamToList(InputStream stream) {
        ArrayList list = new ArrayList();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while((line = reader.readLine()) != null) {
                list.add(line);
            }

            return list;
        } catch (Exception var4) {
            throw new RuntimeException("Can't read Input Stream: " + var4.getMessage());
        }
    }

}