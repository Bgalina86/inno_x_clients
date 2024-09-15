package inno_x_clients.x_clients;

import static inno_x_clients.constClass.Const.HTTP_CODE_CREATE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import inno_x_clients.constClass.Validationbody;
import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.Employee;
import inno_x_clients.x_clients.model.PostEmployeeRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class EmployeeContractTestRestAssurt {

    private static ConfProperties properties;
    EmployeeApiHelper employeeApiHelper;
    private static String username;
    private static String password;


    @BeforeAll
    public static void setUp() {
        properties = new ConfProperties();
        RestAssured.baseURI = properties.getProperty("baseURI");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void setUpL() {
        employeeApiHelper = new EmployeeApiHelper();
    }

    @Test
    @DisplayName("Получение списка сотрудников для компании")
    public void getEmployeeCompany() {

      employeeApiHelper.printGetEmployeeIsCompany(581);

    }

    @Test
    @DisplayName("Добавить нового сотрудника")
    public void iCanAddNewUserCompany() {
        AuthResponse authResponse = employeeApiHelper.auth(username, password);
        PostEmployeeRequest postEmployeeRequest = inno_x_clients.constClass.Validationbody.body;
        given().basePath("employee")
            .body(postEmployeeRequest)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(HTTP_CODE_CREATE)
            .and()
            .body("id", is(greaterThan(0)));
    }

    @Test
    @DisplayName("Получение сотрудника по id")
    public void iCanGetUserCompany() {
        AuthResponse authResponse = employeeApiHelper.auth(username, password);

        int idResponse = given().basePath("employee")
            .body(Validationbody.body)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .body()
            .jsonPath()
            .getInt("id");

        int idRequest = employeeApiHelper.getEmployeeInfo(idResponse).id();
        assertEquals(idRequest, idResponse);
    }

    //
    @Test
    @DisplayName("Редактируем запись о сотруднике")
    public void iCanCreateEmplyee() {
        AuthResponse authResponse = employeeApiHelper.auth(username, password);

        int idReqest = given().basePath("employee")
            .body(Validationbody.body)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .body()
            .jsonPath()
            .getInt("id");

        String lastNameRequest = employeeApiHelper.getEmployeeInfo(idReqest).lastName();
        //меняем
        given().basePath("employee")
            .body(Validationbody.bodyCreate)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", idReqest)
            .body().as(Employee.class).lastName();
        String lastNameResponse = employeeApiHelper.getEmployeeInfo(idReqest).lastName();
        assertNotEquals(lastNameRequest, lastNameResponse);
    }
}
