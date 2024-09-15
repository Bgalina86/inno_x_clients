package inno_x_clients.x_clients.helper;

import static io.restassured.RestAssured.given;

import inno_x_clients.x_clients.model.AuthRequest;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.CreateEmployeeResponse;
import inno_x_clients.x_clients.model.Employee;
import inno_x_clients.x_clients.model.PatchEmployeeRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;


public class EmployeeApiHelper {
    private static ConfProperties properties;
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

    public AuthResponse auth(String username, String password) {
        AuthRequest authRequest = new AuthRequest(username, password);

        return given()
            .basePath("/auth/login")
            .body(authRequest)
            .contentType(ContentType.JSON)
            .when()
            .post()
            .as(AuthResponse.class);
    }

    public Employee printGetEmployeeIsCompany(int id) {

        return given()
            .basePath("employee")
            .queryParam("company", id)
            .when()
            .get()
            .body().as(Employee.class);
    }


    public Object printGetEmployeeId(int id) {
        return given().basePath("employee")
            .when()
            .get("{id}", id)
            .body().prettyPrint();

    }

    public CreateEmployeeResponse createEmployee(Employee employee) {
        AuthResponse authResponse = auth(username,password);
        return given()
            .basePath("employee")
            .body(employee)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post().body().as(CreateEmployeeResponse.class);
    }

    public Employee getEmployeeInfo(int employeeId) {
          return given()
            .basePath("employee")
            .when()
            .get("{Id}", employeeId).body().as(Employee.class);

    }

    public List<Employee> getListOfEmployee(int companyId) {

        return given()
            .basePath("employee")
            .queryParam("company", companyId)
            .when()
            .get().body().as(new TypeRef<>() {
            });

    }

    public Employee editEmployee(int employeeId, PatchEmployeeRequest patchEmployeeRequest) {
        AuthResponse authResponse = auth(username,password);

        return given()
            .basePath("employee")
            .body(patchEmployeeRequest)
            .header("x-client-token", authResponse.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}", employeeId).body().as(Employee.class);
    }
}
