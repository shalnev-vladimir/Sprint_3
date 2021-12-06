package scooter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewOrderData {

    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public ScooterColor[] color;

    public NewOrderData(String firstName, String lastName, String address, int metroStation, String phone,
                        int rentTime, String deliveryDate, String comment, ScooterColor[] color) { // ScooterColor[] color
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static NewOrderData getRandom() {

        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final int metroStation = RandomUtils.nextInt();
        final String phone = RandomStringUtils.randomAlphabetic(10);
        final int rentTime = RandomUtils.nextInt();
        final String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        final String comment = RandomStringUtils.randomAlphabetic(10);
        final ScooterColor[] color = new ScooterColor[]{};
        return new NewOrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    public NewOrderData setColor(ScooterColor[] color) {
        this.color = color;
        return this;
    }
}
