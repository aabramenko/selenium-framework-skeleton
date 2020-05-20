package project.core;

import project.models.Db;

public class DbManager {
    public static Db getNewDbInstance() {
        return new Db(
                TestRunParams.getDbServer(),
                TestRunParams.getDbPort(),
                TestRunParams.getDbUsername(),
                TestRunParams.getDbPassword(),
                null,
                null
        );
    }
}