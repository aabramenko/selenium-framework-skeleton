package project.core;

import project.models.Db;

public class DbManager {
    public static Db getNewDbInstance() {
        return new Db(
                ConfigManager.getDbServer(),
                ConfigManager.getDbPort(),
                ConfigManager.getDbUsername(),
                ConfigManager.getDbPassword(),
                null,
                null
        );
    }
}