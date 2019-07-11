package project.core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;

public class ConfigManager {

    private final static String runConfigTag = Constants.RUN_CONFIG_FILE_MAIN_TAG;
    private final static String envConfigTag = Constants.ENV_CONFIG_FILE_MAIN_TAG;

    private static Logger log = Logger.getLogger("");

    private static Config runConfig;
    private static Config envConfig;

    public static Config getRunConfig() {
        return runConfig;
    }

    public static void uploadRunConfigValues() {
        log.info("uploading run config parameters");
        String valFromSystem = System.getProperty("run_config");
        String configFileName = "run_config_main";
        if (valFromSystem != null) {
            configFileName = valFromSystem;
        }
        log.info("run config file is: " + configFileName);
        runConfig = ConfigFactory.load(configFileName);
    }

    public static void uploadEnvConfigValues() {
        log.info("uploading environment config parameters");
        String valFromSystem = System.getProperty("env_config");
        String configFileName = "env_config_main";
        if (valFromSystem != null) {
            configFileName = valFromSystem;
        }
        log.info("env config file is: " + configFileName);
        envConfig = ConfigFactory.load(configFileName);
    }

    public static String getPathToSampleFilesFolder() {
        String path = System.getProperty("user.dir") + runConfig.getString(runConfigTag + ".path_to_sample_files");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    public static String getPathToTestFilesFolder() {
        String path = System.getProperty("user.dir") + runConfig.getString(runConfigTag + ".path_to_temp_files");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    public static String getBrowserName() {
        if (System.getProperty("browser") != null) {
            return System.getProperty("browser");
        }
        else {
            return envConfig.getString(envConfigTag + ".browser");
        }
    }

    public static int getWaitForPageUploadSec() {
        return envConfig.getInt(envConfigTag + ".wait_for_page_upload_sec");
    }

    public static String getGridHubUrl() {
        return runConfig.getString(runConfigTag + ".grid_hub_url");
    }

    public static String getGridHost() {
        return runConfig.getString(runConfigTag + ".grid_host");
    }

    public static boolean isGrid() {
        return Boolean.valueOf(runConfig.getString(runConfigTag + ".use_grid"));
    }

    public static boolean isSelenoid() {
        return Boolean.valueOf(runConfig.getString(runConfigTag + ".use_selenoid"));
    }

    public static boolean isHeadless() {
        if (System.getProperty("headless") != null) {
            return Boolean.valueOf(System.getProperty("headless"));
        }
        else {
            return Boolean.valueOf(runConfig.getString(runConfigTag + ".headless"));
        }
    }

    public static boolean isScreenOnFailure() {
        return Boolean.valueOf(runConfig.getString(runConfigTag + ".screenshot_on_test_failure"));
    }

    public static boolean isScreenOnSuccess() {
        return Boolean.valueOf(runConfig.getString(runConfigTag + ".screenshot_on_test_success"));
    }

    public static boolean isHtmlOnFailure() {
        return Boolean.valueOf(runConfig.getString(runConfigTag + ".html_source_on_test_failure"));
    }

    public static boolean isHtmlOnSuccess() {
        return Boolean.valueOf(runConfig.getString(runConfigTag + ".html_source_on_test_success"));
    }
}