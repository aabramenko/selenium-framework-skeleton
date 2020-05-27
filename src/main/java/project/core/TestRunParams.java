package project.core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;

public class TestRunParams {

    private static final Logger log = Logger.getLogger("");

    private final static String runParametersTag = Constants.RUN_PARAMETERS_FILE_MAIN_TAG;
    private final static String dbConfigTag = Constants.DB_CONFIG_FILE_MAIN_TAG;

    private static Config runParameters = null;
    private static Config dbConfig = null;

    private static final String fixedTimeStamp = Utils.getCurrentDate("MM-dd-HH-mm-ss");

    private static String getFixedTimeStamp() {
        return fixedTimeStamp;
    }

    public static void uploadDefaultRunParameters() {
        log.info("uploading of default run parameters");
        String configFileName = "default_run_parameters";
        runParameters = ConfigFactory.load(configFileName);
    }

    public static void uploadDbConfigValues() {
        log.info("uploading db configs");
        String configFileName = "db_config";
        log.info("db config file is: " + configFileName);
        dbConfig = ConfigFactory.load(configFileName);
    }

    public static String getPathToSampleFilesFolder() {
        String path = System.getProperty("user.dir") + runParameters.getString(runParametersTag + ".path_to_sample_files");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    public static String getPathToTestFilesFolder() {
        String path = System.getProperty("user.dir") + runParameters.getString(runParametersTag + ".path_to_temp_files");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    public static String getDefinedBrowserName() {
        if (System.getProperty("BROWSER") != null) {
            return System.getProperty("BROWSER");
        }
        else {
            return runParameters.getString(runParametersTag + ".browser");
        }
    }

    public static String getDbUsername() {
        return dbConfig.getString(dbConfigTag + ".username");
    }

    public static String getDbPassword() {
        return dbConfig.getString(dbConfigTag + ".password");
    }

    public static String getDbServer() {
        return dbConfig.getString(dbConfigTag + ".server");
    }

    public static String getDbPort() {
        return dbConfig.getString(dbConfigTag + ".port");
    }

    public static int getWaitForPageUploadSec() {
        return runParameters.getInt(runParametersTag + ".wait_for_page_upload_sec");
    }

    public static boolean isGrid() {
        if (System.getProperty("GRID") != null) {
            return Boolean.parseBoolean(System.getProperty("GRID"));
        }
        else {
            return Boolean.parseBoolean(runParameters.getString(runParametersTag + ".grid"));
        }
    }

    public static String getHubHost() {
        String host = "localhost";
        if (System.getProperty("HUB_HOST") != null) {
            host = System.getProperty("HUB_HOST");
        }
        return host;
    }

    public static boolean isHeadless() {

        log.info("~~~~~ HEADLESS = " + System.getProperty("HEADLESS") );

        if (System.getProperty("HEADLESS") != null) {
            return Boolean.parseBoolean(System.getProperty("HEADLESS"));
        }
        else {
            return Boolean.parseBoolean(runParameters.getString(runParametersTag + ".headless"));
        }
    }

    public static boolean isVideo() {
        if (System.getProperty("VIDEO") != null) {
            return Boolean.parseBoolean(System.getProperty("VIDEO"));
        }
        else {
            return false;
        }
    }

    public static boolean isScreenOnFailure() {
        return Boolean.parseBoolean(runParameters.getString(runParametersTag + ".screenshot_on_test_failure"));
    }

    public static boolean isScreenOnSuccess() {
        return Boolean.parseBoolean(runParameters.getString(runParametersTag + ".screenshot_on_test_success"));
    }

    public static boolean isHtmlOnFailure() {
        return Boolean.parseBoolean(runParameters.getString(runParametersTag + ".html_source_on_test_failure"));
    }

    public static boolean isHtmlOnSuccess() {
        return Boolean.parseBoolean(runParameters.getString(runParametersTag + ".html_source_on_test_success"));
    }

    public static String getNameOfFolderWithAllTestRunArtifacts() {
        return "test-run-artifacts/";
    }

    public static String getPathToAllArtifactsFolders() {
        return System.getProperty("user.dir") + "/target/" + getNameOfFolderWithAllTestRunArtifacts();
    }

    public static String getPathToCurrentArtifactsFolder() {
        return getPathToAllArtifactsFolders() + getNameOfCurrentRunLogFolder();
    }

    public static String getNameOfCurrentRunLogFolder() {
        return "run_" + getFixedTimeStamp() + "/";
    }

    public static String getPathToDownloads() {
        return getPathToTestFilesFolder();
    }

    public static String getScreenshotsLocation() {
        return getPathToCurrentArtifactsFolder();
    }
}
