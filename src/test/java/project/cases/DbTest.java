package project.cases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import project.core.DbManager;
import project.data.SqlQueries;
import project.models.Db;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("DB test")
@Test(groups = {"regression", "db"})
public class DbTest extends AbstractTest {

    Db db;

    @BeforeClass(alwaysRun = true)
    void setup() {
        db = DbManager.getNewDbInstance();
        db.connect();
    }

    @AfterClass(alwaysRun = true)
    void tearDown() {
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
