package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders";

    // создание заказа
    @DisplayName("Check if it's possible to create an order")
    @Description("Basic positive test that validate you are allowed to order scooter with any available color")
    @Step("Sends POST request to " + ORDER_PATH + " endpoint")
    public ValidatableResponse createOrder(NewOrderData newOrderData) {
        return given()
                .spec(getBaseSpec())
                .body(newOrderData)
                .when()
                .post(ORDER_PATH)
                .then();
    }
}
