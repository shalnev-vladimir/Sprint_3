package scooter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import io.qameta.allure.Description;
import io.restassured.response.*;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    // проверяем, что курьера можно создать.
    @Test
    @Description("Проверяет, что курьера можно создать.")
    public void checkCourierCanBeCreatedTest() {

        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)); // без фабрики в скобках будет new CourierCredentials(courier.login, courier.password)

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat("Ожидаемый status code 201, фактический" + statusCode, statusCode, equalTo(201));
        assertTrue("Курьер не создался", isCourierCreated);
        assertThat("Невалидный ID курьера", courierId, is(CoreMatchers.not(0)));
    }


    @Test
    @Description("Проверяет, что курьера можно создать, не указывая firstName при регистрации.")
    public void checkCourierCanBeCreatedWithoutFirstNameTest() {

        Courier courier = Courier.getWithLoginAndPasswordOnly();
        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat("Ожидаемый status code 201, фактический" + statusCode, statusCode, equalTo(201));
        assertTrue("Курьер не создался", isCourierCreated);
        assertThat("Невалидный ID курьера", courierId, is(CoreMatchers.not(0)));
    }

    // проверяем, что нельзя создать двух одинаковых курьеров.
    @Test
    @Description("Проверяет, что нельзя создать двух одинаковых курьеров.")
    public void checkTwoIdenticalCouriersCanNotBeCreated() {

        String thisLoginIsOccupied = "Этот логин уже используется";
        courierClient.create(courier);

        ValidatableResponse negativeResponse = courierClient.create(courier);

        int statusCodeNegativeResponse = negativeResponse.extract().statusCode();
        int code = negativeResponse.extract().path("code");
        String message = negativeResponse.extract().path("message");
        courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));

        assertThat(statusCodeNegativeResponse, equalTo(409));
        assertThat("Ожидаемый статус код 409, фактический" + code, code, (equalTo(409)));
        assertThat("Ожидаемый текст ошибки: " + thisLoginIsOccupied + ". Фактический: " + message,
                message, (equalTo(thisLoginIsOccupied)));
    }

    @Test
    @Description("Проверяет, что в теле ответа возвращается ok: true")
    public void checkSuccessRequestReturnsOkTrueTest() {

        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        ResponseBodyExtractionOptions body = response.extract().body();

        assertTrue("Тело ответа не соответствует ожидаемому", body.jsonPath().getBoolean("ok"));
    }
}