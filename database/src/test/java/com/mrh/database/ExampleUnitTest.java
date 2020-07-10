package com.mrh.database;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Assert.assertTrue(Pattern.matches("^(create).*(.sql)$", "create_1.sql"));
        Assert.assertTrue(Pattern.matches("^(create).*(.sql)$", "createasjhb.sql"));
        Assert.assertFalse(Pattern.matches("^(create).*(.sql)$", "creat_1.sql"));
        Assert.assertTrue(Pattern.matches("^(upgrade_)\\d+(.sql)$", "upgrade_2.sql"));
        Assert.assertTrue(Pattern.matches("^(upgrade_)\\d+(.sql)$", "upgrade_22.sql"));
        Assert.assertTrue(Pattern.matches("^(upgrade_)\\d+(.sql)$", "upgrade_22444.sql"));
    }
}