package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderListClient extends RestAssuredClient{

    private static final String ORDER_PATH = "/api/v1/orders";

    @DisplayName("Check if it's possible to get list of orders")
    @Description("Positive test that returns orders list")
    @Step("Sends GET request to /api/v1/courier endpoint")
    public ValidatableResponse orderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
