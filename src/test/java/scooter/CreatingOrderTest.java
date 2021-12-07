package scooter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreatingOrderTest {

    private OrderClient orderClient;
    NewOrderData newOrderData;
    private ScooterColor[] color;
    private int expectedStatus;

    public CreatingOrderTest(ScooterColor[] color, int expectedStatus) {
        this.color = color;
        this.expectedStatus = expectedStatus;
    }

    @Parameterized.Parameters
    public static Object[][] getColourData() {
        return new Object[][] {
                {new ScooterColor[] {ScooterColor.BLACK, ScooterColor.GRAY}, 201},
                {new ScooterColor[] {ScooterColor.BLACK}, 201},
                {new ScooterColor[] {ScooterColor.GRAY}, 201},
                {new ScooterColor[] {null}, 201}
        };
    }

    @Test
    @Description("Проверяет, что можно заказать самокат черного цвета, серого, " +
            "обоих цветов или вообще не указывать цвет при заказе.")
    public void creatingOrder() {

        orderClient = new OrderClient();
        newOrderData = NewOrderData.getRandom().setColor(color);

        ValidatableResponse response = orderClient.createOrder(newOrderData);

        int statusCode = response.extract().statusCode();
        int bodyResponseTrackLine = response.extract().path("track");

        assertThat("Ожидаемый код ответа: " + 201 + ". Фактический: " + statusCode, statusCode, equalTo(201));
        assertThat("Невалидный номер заказа (" + bodyResponseTrackLine + ")",
                bodyResponseTrackLine, is(CoreMatchers.not(0)));
    }
}
