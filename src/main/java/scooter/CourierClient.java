package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    // создание курьера
    @DisplayName("Check if it's possible to create a courier")
    @Step("Sends POST request to /api/v1/courier endpoint")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    // действие, которое логинит курьера
    @DisplayName("Check if it's possible to login and returns courier ID")
    @Description("Confirms you are successfully logged in")
    @Step("Sends POST request to /api/v1/courier/login endpoint")
    public int login(CourierCredentials credentials) { // был тип int
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    // удаляем курьера
    @DisplayName("Check if it's possible to delete a courier")
    @Description("Confirms courier account is deleted")
    @Step("Sends DELETE request to /api/v1/courier endpoint and delete courier using courier's ID")
    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("ok");
    }

    @DisplayName("Check if it's possible to login")
    @Description("Confirms you are allowed to login if you have filled out all required fields")
    @Step("Sends POST request to /api/v1/courier/login endpoint")
    public ValidatableResponse courierLogin(CourierCredentials credentials) { // был тип int
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }
}

