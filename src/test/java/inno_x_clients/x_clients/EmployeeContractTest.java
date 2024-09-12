package inno_x_clients.x_clients;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import inno_x_clients.constClass.Validationbody;
import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.CreateEmployeeReqest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class EmployeeContractTest {

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
        employeeApiHelper.printEmployeeIsCompony(581);
    }

    @Test
    @DisplayName("Добавить нового сотрудника")
    public void iCanAddNewUserCompany() {
        AuthResponse info = employeeApiHelper.auth(username, password);
        CreateEmployeeReqest createEmployeeReqest = inno_x_clients.constClass.Validationbody.body;
        RestAssured.given().basePath("employee")
            .body(createEmployeeReqest)
            .header("x-client-token", info.userToken())
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
    AuthResponse info = employeeApiHelper.auth(username, password);
    CreateEmployeeReqest createEmployeeReqest = Validationbody.body;
    int id = RestAssured.given().basePath("employee")
            .body(createEmployeeReqest)
            .header("x-client-token", info.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .body()
            .jsonPath()
            .getInt("id");
    employeeApiHelper.printEmployeeIsCompony(id);


}

//    @ParameterizedTest
//    @ArgumentsSource(AddNewUserCompany.class)
//    public void iCanAddNewUserCompanyParameter(Employee createEmployeeReqest) {
//        AuthResponse info = employeeApiHelper.auth(username, password);
//        //CreateEmployeeReqest employeeReqest = new CreateEmployeeReqest(createEmployeeReqest);
//        Response response = RestAssured.given()
//            .basePath("employee")
//            .body(createEmployeeReqest)
//            .header("x-client-token", info.userToken())
//            .contentType(ContentType.JSON)
//            .when()
//            .post();
//        int id = response.body().jsonPath().getInt("id");
//        response.then()
//            .assertThat()
//            .statusCode(HTTP_CODE_CREATE)
//            .and()
//            .body("id", is(greaterThan(0)));
//        System.out.println(id);

//        RestAssured.given().basePath("employee")
//            .body(createEmployeeReqest).header("x-client-token", info.userToken()).contentType(ContentType.JSON)
//            .when().post().then().assertThat().statusCode(201)
//            .and().body("id", is(greaterThan(0)));
   // }
}
