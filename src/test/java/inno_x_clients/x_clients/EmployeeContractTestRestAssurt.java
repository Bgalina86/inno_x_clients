package inno_x_clients.x_clients;

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
    private static int companyId;


    @BeforeAll
    public static void setUp() {
        properties = new ConfProperties();
        RestAssured.baseURI = properties.getProperty("baseURI");
         username = properties.getProperty("username");
         password = properties.getProperty("password");
        companyId = Integer.parseInt(properties.getProperty("companyId"));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void setUpL() {
        employeeApiHelper = new EmployeeApiHelper();
    }

    @Test
    @DisplayName("Получение списка сотрудников для компании")
    public void getEmployeeCompany() {

        employeeApiHelper.printGetEmployeeIsCompany(companyId);

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
            //.statusCode(HTTP_CODE_CREATE)
           // .and()
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

    @Test
    @DisplayName("Редактируем запись о сотруднике. Поле Фамилия")
    public void iCanCreateEmployeeLastName() {

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
        given().basePath("employee")
            .body(Validationbody.bodyCreate)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", idReqest)
            .body().as(Employee.class).lastName();
        String lastNameResponse = employeeApiHelper.getEmployeeInfo(idReqest).lastName();
        assertNotEquals(lastNameRequest, lastNameResponse, "Редактируется поле Фамилия");
    }

    @Test
    @DisplayName("Редактируем запись о сотруднике. Поле номер телефона")
    public void iCanCreateEmployeePhone() {
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

        String phoneRequest = employeeApiHelper.getEmployeeInfo(idReqest).phone();
        given().basePath("employee")
            .body(Validationbody.bodyCreate)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", idReqest)
            .body().as(Employee.class).phone();
        String phoneResponse = employeeApiHelper.getEmployeeInfo(idReqest).phone();
        assertNotEquals(phoneRequest, phoneResponse, "Не редактируется поле номер телефона");
    }

    @Test
    @DisplayName("Редактируем запись о сотруднике. Поле Email")
    public void iCanCreateEmployeeEMAIL() {
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

        String emailRequest = employeeApiHelper.getEmployeeInfo(idReqest).email();
        given().basePath("employee")
            .body(Validationbody.bodyCreate)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", idReqest)
            .body().as(Employee.class).email();
        String emailResponse = employeeApiHelper.getEmployeeInfo(idReqest).email();
        assertNotEquals(emailRequest, emailResponse, "Не редактируется поле email");
    }
//    private int createNewCompany(){
//        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest("TecnaSchool",
//            "Онлайн-курсы");
//      int companyId = given()
//            .basePath("company")
//            .body(createCompanyRequest)
//            .when()
//            .post()
//            .jsonPath()
//            .getInt("id");
//    }
}
