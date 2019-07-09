package project.cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("Debug")
@Test(groups = {"regression"})
public class DebugTest extends AbstractTest {

    @Test
    public void tc_dbg_01() {
        log("the first case");
        Assert.assertEquals(
                "test1",
                "test2",
                "strings"
        );
    }

    @Test
    public void tc_dbg_02() {
        log("the second case");
        Assert.assertEquals(
                "test3",
                "test3",
                "strings"
        );
    }

}
