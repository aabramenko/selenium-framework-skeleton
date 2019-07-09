package project.core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;

public class ConfigManager {

    private final static String runConfigMainTag = "run_config";
    private final static String envConfigMainTag = "env_config";
    private final static String credsMainTag = "creds";

    private static Logger log = Logger.getLogger("");

    private static Config runConfig;
    private static Config envConfig;
    private static Config creds;

    public static Config getRunConfig() {
        return runConfig;
    }

    public static Config getEnvConfig() {
        return envConfig;
    }

    public static Config getCreds() {
        return creds;
    }

    public static void uploadRunConfigValues() {
        log.info("uploading run config parameters");
        String valFromSystem = System.getProperty("config");
        String valDefault = "run_config_main";
        String configFileName = valDefault;
        if (valFromSystem != null) {
            configFileName = valFromSystem;
        }
        log.info("run config file is: " + configFileName);
        runConfig = ConfigFactory.load(configFileName);
    }

    public static void uploadEnvConfigValues(Config runConfig) {
        log.info("uploading environment config parameters");
        String envConfigFile = runConfig.getString(runConfigMainTag + ".env_config_file");
        log.info("env config file is: " + envConfigFile);
        envConfig = ConfigFactory.load(envConfigFile);
    }

    public static void uploadCredsValues(Config runConfig) {
        log.info("uploading credentials");
        String credFile = runConfig.getString(runConfigMainTag + ".cred_file");
        log.info("creds file is: " + credFile);
        creds = ConfigFactory.load(credFile);
    }

    public static String getPathToSampleFilesFolder() {
        String path = System.getProperty("user.dir") + runConfig.getString(runConfigMainTag + ".path_to_sample_files");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    public static String getPathToTestFilesFolder() {
        String path = System.getProperty("user.dir") + runConfig.getString(runConfigMainTag + ".path_to_temp_files");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    public static String getStartUrl() {
        return envConfig.getString(envConfigMainTag + ".start_url");
    }

    public static String getBrowserName() {
        if (System.getProperty("browser") != null) {
            return System.getProperty("browser");
        }
        else {
            return envConfig.getString(envConfigMainTag + ".browser");
        }
    }

    public static int getWaitForPageUploadSec() {
        return envConfig.getInt(envConfigMainTag + ".wait_for_page_upload_sec");
    }

    public static String getGridHubUrl() {
        return runConfig.getString(runConfigMainTag + ".grid_hub_url");
    }

    public static String getGridHost() {
        return runConfig.getString(runConfigMainTag + ".grid_host");
    }

    public static boolean isGrid() {
        return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".use_grid"));
    }

    public static boolean isSelenoid() {
        return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".use_selenoid"));
    }

    public static boolean isHeadless() {
        if (System.getProperty("headless") != null) {
            return Boolean.valueOf(System.getProperty("headless"));
        }
        else {
            return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".headless"));
        }
    }

    public static boolean isScreenOnFailure() {
        return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".screenshot_on_test_failure"));
    }

    public static boolean isScreenOnSuccess() {
        return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".screenshot_on_test_success"));
    }

    public static boolean isHtmlOnFailure() {
        return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".html_source_on_test_failure"));
    }

    public static boolean isHtmlOnSuccess() {
        return Boolean.valueOf(runConfig.getString(runConfigMainTag + ".html_source_on_test_success"));
    }
}