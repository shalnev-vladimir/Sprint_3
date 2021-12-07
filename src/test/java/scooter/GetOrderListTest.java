package scooter;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;

public class GetOrderListTest {

    // проверяет, что тело ответа непустое.
    @Test
    @Description("Проверяет, что в теле ответа приходит список заказов.")
    public void checkOrderListBodyIsNotEmpty() {

        OrderListClient orderlistClient = new OrderListClient();
        ValidatableResponse response = orderlistClient.orderList();

        ArrayList<String> responseBody = response.extract().path("orders");

        assertThat(responseBody, is(notNullValue()));
    }
}
