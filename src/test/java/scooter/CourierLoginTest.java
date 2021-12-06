package scooter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {

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

    // проверяет, что курьер может авторизоваться.
    @Test
    @Description("Проверяет, что курьер может авторизоваться.")
    public void checkCourierCanAuthorize() {

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));
        ValidatableResponse response = courierClient.courierLogin(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        int courierId = response.extract().path("id");

        assertThat("Курьер не может авторизоваться с валидными данными", statusCode, equalTo(200));
        assertThat(courierId, is(CoreMatchers.not(0)));
    }

    // проверяет, что нельзя авторизоваться с неправильноым логином.
    @Test
    @Description("Проверяет, что нельзя авторизоваться с неправильным логином.")
    public void canNotAuthorizeWithInvalidLogin() {

        String invalidLogin = "itIsMyAccountHonestly";

        courierClient.create(courier);
        ValidatableResponse response = courierClient.courierLogin(new CourierCredentials(invalidLogin, courier.password));
        courierId = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("В приложении возможна авторизация без логина", statusCode, equalTo(404));
        assertThat(errorMessage, equalTo("Учетная запись не найдена"));
    }

    // проверяет, что нельзя авторизоваться с неправильным паролем.
    @Test
    @Description("Проверяет, что нельзя авторизоваться с неправильным паролем.")
    public void canNotAuthorizeWithInvalidPassword() {

        String invalidPassword = "letMeIn";

        courierClient.create(courier);
        ValidatableResponse response = courierClient.courierLogin(new CourierCredentials(courier.login, invalidPassword));
        courierId = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("В приложении возможна авторизация без логина", statusCode, equalTo(404));
        assertThat(errorMessage, equalTo("Учетная запись не найдена"));
    }


    // проверяет, что нельзя авторизоваться без логина
    @Test
    @Description("Проверяет, что нельзя авторизоваться без логина.")
    public void courierCanNotLoginWithoutLogin() {

        courierClient.create(courier);
        ValidatableResponse response = courierClient.courierLogin(new CourierCredentials(null, courier.password));
        courierId = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("В приложении возможна авторизация без логина", statusCode, equalTo(400));
        assertThat(errorMessage, equalTo("Недостаточно данных для входа"));
    }

    // проверяет, что нельзя авторизоваться без пароля. Этот тест всегда падает из-за таймаута. Баг в приложении
    @Test
    @Description("Проверяет, что нельзя авторизоваться без пароля.")
    public void courierCanNotLoginWithoutPassword() {

        courierClient.create(courier);
        ValidatableResponse response = courierClient.courierLogin(new CourierCredentials(courier.login, null));
        courierId = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("В приложении возможна авторизация без пароля", statusCode, equalTo(400));
        assertThat(errorMessage, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Проверяет, что нельзя авторизоваться под несуществующим пользователем.")
    public void authorisationUnderANonExistentUserNegativeTest() {

        courierClient.create(courier);
        ValidatableResponse response =
                courierClient.courierLogin(new CourierCredentials("NonExistentLogin", "NonExistentLogin"));
        courierId = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat(statusCode, equalTo(404));
        assertThat(errorMessage, equalTo("Учетная запись не найдена"));
    }
 }
