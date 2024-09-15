package inno_x_clients.x_clients;

import static inno_x_clients.constClass.Const.*;
import static inno_x_clients.x_clients.helper.EmployeeRandomeService.generateEmployee;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.github.javafaker.Faker;
import inno_x_clients.x_clients.ext.DatabaseService;
import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.Employee;
import inno_x_clients.x_clients.model.PatchEmployeeRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.sql.SQLException;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeContractTestBD {

    private  ConfProperties properties;
    EmployeeApiHelper employeeApiHelper;
    private  String dbUsername;
    private  String dbPassword;
    private  String username;
    private  String password;
    private  String headers;
    private DatabaseService databaseService;
    private int companyId;
    private int employeeId;

    @BeforeEach
    public  void setUp() throws SQLException, IOException {

        properties = new ConfProperties();
        dbUsername = properties.getProperty("dbUsername");
        dbPassword = properties.getProperty("dbPassword");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        headers = properties.getProperty("headers");
        employeeApiHelper = new EmployeeApiHelper();

        RestAssured.baseURI = properties.getProperty("baseURI");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        databaseService = new DatabaseService();
        databaseService.connectToDb();
        companyId = databaseService.createNewCompany();
        employeeId = databaseService.createNewEmployee(companyId);
    }

    @AfterEach
    public  void tearDown() throws SQLException {
        databaseService.closeConnection();
    }

    @Test
    @Description("Тест на соединение с БД")
    public void connectToDb() throws SQLException {
        this.employeeId = databaseService.getAnyEmployeeId();
    }


    @Test
    @Description("Получает список работников по существующему id компании")
    public void status200OnGetEmployeesByCompany() throws SQLException {
        int id = databaseService.getAnyCompanyID();
        given()
            .basePath("employee")
            .queryParam("company", id)
            .when()
            .get()
            .then()
            .statusCode(HTTP_CODE_OK)
            .header("Content-Type", headers);
    }

    @Test
    @Description("Ожидаем пустое тело по НЕ существующему id компании")
    public void status200AndEmptyBodyOnGetEmployeesByCompany() throws SQLException {
        int id = databaseService.getLastCompanyID();
        given()
            .basePath("employee")
            .queryParam("company", id + 1)
            .when()
            .get()
            .then()
            .statusCode(HTTP_CODE_OK)
            .header("Content-Type", headers)
            .body(equalTo("[]"));
    }

    @Test
    @Description("Ожидается статус 200, при получение сотрудника по его id")
    public void status200OnGettingEmployeerById() throws SQLException {

        int id = databaseService.getAnyEmployeeId();
        given()
            .basePath("employee")
            .when()
            .get("{Id}", id)
            .then()
            .statusCode(HTTP_CODE_OK)
            .header("Content-Type", headers);

    }

    @Test
    @Description("Ожидается статус 200 и Content-length=0, при получение сотрудника по несуществующему id")
    public void status200OnGettingEmployeerByInvalidId() throws SQLException {

        int id = databaseService.getLastEmployeeID();
        given()
            .basePath("employee")
            .when()
            .get("{Id}", id + 1)
            .then()
            .statusCode(HTTP_CODE_OK)
            .header("Content-length", equalTo("0"));
    }

    @Test
    @Description("Проверяем, что не  можем создать сотрудника без токеном, status-401")
    public void iCannotAddNewEmployee() {
        Faker faker = new Faker();
        Employee createEmployeeRequest = generateEmployee(faker, companyId);
        given()
            .basePath("employee")
            .body(createEmployeeRequest)
            .contentType(ContentType.JSON)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(HTTP_CODE_UNAUTHORIZED);
    }

    @Test
    @Description("Проверяем что можем создать сотрудника c токеном")
    public void iCanAddNewEmployee() {

        AuthResponse authResponse = employeeApiHelper.auth(username, password);
        Faker faker = new Faker();
        Employee createEmployeeRequest = generateEmployee(faker, companyId);
        given()
            .basePath("employee")
            .body(createEmployeeRequest)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(HTTP_CODE_CREATE);
    }

    @Test
    @Description("Проверяем, что можем изменить информацию о сотруднике")
    public void iCanEditEmployee(){
        AuthResponse authResponse = employeeApiHelper.auth(username, password);
        PatchEmployeeRequest patchEmployeeRequest = fakerEmploee();
        given()
            .basePath("employee")
            .body(patchEmployeeRequest)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", employeeId)
            .then()
            .assertThat()
            .statusCode(HTTP_CODE_OK);
    }

    @Test
    @Description("Проверяем, что при отправке запросы на изменение несуществующего сотрудника получаем 500")
    public void iCannotEditEmployee(){
        AuthResponse authResponse = employeeApiHelper.auth(username, password);
        PatchEmployeeRequest patchEmployeeRequest = fakerEmploee();

        given()
            .basePath("employee")
            .body(patchEmployeeRequest)

            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", employeeId + 1000)
            .then()
            .assertThat()
            .statusCode(HTTP_CODE_INTERNAL_SERVER_ERROR);
    }

    public PatchEmployeeRequest fakerEmploee() {
        Faker faker = new Faker();
       return new PatchEmployeeRequest(faker.name().lastName(),
           faker.internet().emailAddress(),
           faker.internet().url(),
           faker.phoneNumber().phoneNumber(),
           faker.bool().bool());
    }

}
