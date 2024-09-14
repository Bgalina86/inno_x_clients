package inno_x_clients.x_clients.helper;

import static io.restassured.RestAssured.given;

import inno_x_clients.x_clients.ext.EnvProperties;
import inno_x_clients.x_clients.model.AuthRequest;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.CreateEmployeeResponse;
import inno_x_clients.x_clients.model.Employee;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.util.List;


public class EmployeeApiHelper<PatchEmployeeRequest> {

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

    public Object printGetEmployeeIsCompany(int id) {
        //    https://x-clients-be.onrender.com/employee?company=4933
        //    curl -X 'GET' \
        //  'https://x-clients-be.onrender.com/employee?company=4933' \
        //  -H 'accept: application/json'
        return given()
            .basePath("employee")
            .queryParam("company", id)
            .when()
            .get()
            .body().prettyPrint();
    }

//public  Object postNewUserIsCompony(String authRequest){
//           return given().basePath("employee")
//               .body(EmployeeRandomeService.class)
//               .header("x-client-token", authRequest)
//               .contentType(ContentType.JSON)
//               .when()
//               .post()
//               .body()
//               .prettyPrint();
//}
    public Object printGetEmployeeId(int id) {
//        curl -X 'GET' \
//  'https://x-clients-be.onrender.com/employee/674' \
//  -H 'accept: application/json'
        return given().basePath("employee")
            .when()
            .get("{id}", id)
            .body().prettyPrint();

    }

    public CreateEmployeeResponse createEmployee(Employee employee) {
        AuthResponse info = auth("leyla", "water-fairy");
        return given()
            .basePath("employee")
            .body(employee)
            .header("x-client-token", info.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post().body().as(CreateEmployeeResponse.class);
    }
    public Employee getEmployeeInfo(int employeeId) throws IOException {

        AuthResponse info = auth(EnvProperties.getEnvProperties("app_user.login"), EnvProperties.getEnvProperties("app_user.pass"));

        return given()
            .basePath("employee")
            .when()
            .get("{Id}", employeeId).body().as(Employee.class);

    }

    public List<Employee> getListOfEmployee(int companyId) throws IOException {
        AuthResponse info = auth(EnvProperties.getEnvProperties("app_user.login"), EnvProperties.getEnvProperties("app_user.pass"));

        return given()
            .basePath("employee")
            .queryParam("company", companyId)
            .when()
            .get().body().as(new TypeRef<>() {
            });

    }

    public Employee editEmployee(int employeeId, PatchEmployeeRequest patchEmployeeRequest) throws IOException {
        AuthResponse info = auth(EnvProperties.getEnvProperties("app_user.login"), EnvProperties.getEnvProperties("app_user.pass"));

        return given()
            .basePath("employee")
            .body(patchEmployeeRequest)
            .header("x-client-token", info.userToken())
            .contentType(ContentType.JSON)
            .when()
            .patch("{id}",employeeId).body().as(Employee.class);
    }
}
