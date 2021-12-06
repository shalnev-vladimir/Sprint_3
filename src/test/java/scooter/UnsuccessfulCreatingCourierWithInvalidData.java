package scooter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UnsuccessfulCreatingCourierWithInvalidData {

    CourierClient courierClient = new CourierClient();

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public UnsuccessfulCreatingCourierWithInvalidData(Courier courier, int expectedStatus, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][] {
                {Courier.getWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithFirstNameOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithLoginAndFirstNameOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithPasswordAndFirstNameOnly(), 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Test
    public void creatingCourierWithLoginOnlyNegativeTest() {

        ValidatableResponse response = courierClient.create(courier);

        int code = response.extract().path("code");
        String bodyErrorMessage = response.extract().path("message");

        assertThat(code, equalTo(400));
        assertThat(bodyErrorMessage, equalTo("Недостаточно данных для создания учетной записи"));
    }
}
//test