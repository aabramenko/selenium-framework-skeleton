package project.data;

import project.core.Utils;
import project.models.User;

public class TestUsers {

    public static User getUserBadEmail() {
        return new User(
            "",
            "",
            "",
            "",
            "notexistingaddress-" + Utils.getUniqueStringCharsOnly(8) + "@gmail.com"
        );
    }
}
