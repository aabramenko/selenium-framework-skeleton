package com.example.data;

import selenium.core.Utils;
import selenium.example.models.User;

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
