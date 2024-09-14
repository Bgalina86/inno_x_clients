package inno_x_clients.x_clients;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import inno_x_clients.constClass.Validationbody;
import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.PatchEmployeeReqest;
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
        PatchEmployeeReqest patchEmployeeReqest = inno_x_clients.constClass.Validationbody.body;
        given().basePath("employee")
            .body(patchEmployeeReqest)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(201)
            .and()
            .body("id", is(greaterThan(0)));
    }

    @Test
    @DisplayName("Получение сотрудника по id")
    public void iCanGetUserCompany() {
        AuthResponse authResponse = employeeApiHelper.auth(username, password);
        //cоздали сотрудника
       // PatchEmployeeReqest patchEmployeeReqest = Validationbody.body;
        int idRest = given().basePath("employee")
            .body(Validationbody.body)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .body()
            .jsonPath()
            .getInt("id");
       //получаем по id
       employeeApiHelper.printGetEmployeeId(idRest);
    }

    //
    @Test
    @DisplayName("Редактируем запись о сотруднике")
    public void iCanCreateEmplyee() {
        AuthResponse authResponse = employeeApiHelper.auth(username, password);
        //создали
        PatchEmployeeReqest patchEmployeeReqest = Validationbody.body;
        int idReqest = given().basePath("employee")
            .body(patchEmployeeReqest)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .body()
            .jsonPath()
            .getInt("id");
        //запросили по id
      employeeApiHelper.printGetEmployeeId(idReqest);
       //меняем
        given().basePath("employee")
            .body(Validationbody.bodyCreate)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", idReqest)
            .body().prettyPrint();
       employeeApiHelper.printGetEmployeeId(idReqest);
    }
}
