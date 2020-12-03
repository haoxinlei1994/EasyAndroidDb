package com.mrh.easyandroiddb;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final SimpleDateFormat TIME_FORMAT    = new SimpleDateFormat("HH:mm:ss");

    @Test
    public void addition_isCorrect() {
        TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        System.out.println(TIME_FORMAT.format(new Date(3601 * 1000)));
    }
}