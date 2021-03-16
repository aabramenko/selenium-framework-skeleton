package com.example.cases;

import com.example.data.SqlQueries;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.core.DbManager;
import selenium.core.Dictionary;
import selenium.example.models.Db;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("DB test")
@Test(groups = {Dictionary.REGRESSION, Dictionary.DB})
public class DbTest extends AbstractTest {

    Db db;

    @Override
    @BeforeClass(alwaysRun = true)
    protected void beforeClass() {
        db = DbManager.getNewDbInstance();
        db.connect();
    }

    @Override
    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        db.close();
    }

    @Test
    void tc_db_01_get_record() {
        db.query(SqlQueries.TEST_QUERY);

        Assert.assertTrue(
                db.getQueryStringResult("title").contains("at"),
                "response contains 'at'"
        );
    }

    @Test
    void tc_db_02_get_record() {
        db.query(SqlQueries.TEST_QUERY);

        Assert.assertEquals(
                db.getQueryStringResult("internal"),
                "qwerty",
                "internal code"
        );
    }

}
