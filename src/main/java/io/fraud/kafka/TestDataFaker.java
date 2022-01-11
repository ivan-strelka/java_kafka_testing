package io.fraud.kafka;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDataFaker {

    public String date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public String id() {
        return RandomStringUtils.randomNumeric(5);
    }

    public String name() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String status() {
        return "status";
    }


}
