package inno_x_clients.x_clients.helper;

import static io.restassured.RestAssured.given;

import inno_x_clients.x_clients.model.AuthRequest;
import inno_x_clients.x_clients.model.AuthResponse;
import io.restassured.http.ContentType;



public class EmployeeApiHelper {

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

    public void printEmployeeIsCompony(int id) {
        //    https://x-clients-be.onrender.com/employee?company=4933
        //    curl -X 'GET' \
        //  'https://x-clients-be.onrender.com/employee?company=4933' \
        //  -H 'accept: application/json'
        given()
            .basePath("employee")
            .queryParam("id", id)
            .when()
            .get()
            .body().prettyPrint();
    }
   // public CreateEmployeeResponse createEmployee(Employee employee) {
    //        AuthResponse info = auth("leyla", "water-fairy");
    //        return given()
    //                .basePath("employee")
    //                .body(employee)
    //                .header("x-client-token", info.userToken())
    //                .contentType(ContentType.JSON)
    //                .when()
    //                .post().body().as(CreateEmployeeResponse.class);
    //    }
    //
    //    public Employee getEmployeeInfo(int employeeId) throws IOException {
    //
    //        AuthResponse info = auth(EnvProperties.getEnvProperties("app_user.login"), EnvProperties.getEnvProperties("app_user.pass"));
    //
    //        return given()
    //                .basePath("employee")
    //                .when()
    //                .get("{Id}", employeeId).body().as(Employee.class);
    //
    //    }
    //
    //    public List<Employee> getListOfEmployee(int companyId) throws IOException {
    //        AuthResponse info = auth(EnvProperties.getEnvProperties("app_user.login"), EnvProperties.getEnvProperties("app_user.pass"));
    //
    //        return given()
    //                .basePath("employee")
    //                .queryParam("company", companyId)
    //                .when()
    //                .get().body().as(new TypeRef<>() {
    //                });
    //
    //    }
    //
    //    public Employee editEmployee(int employeeId, PatchEmployeeRequest patchEmployeeRequest) throws IOException {
    //        AuthResponse info = auth(EnvProperties.getEnvProperties("app_user.login"), EnvProperties.getEnvProperties("app_user.pass"));
    //
    //        return given()
    //                .basePath("employee")
    //                .body(patchEmployeeRequest)
    //                .header("x-client-token", info.userToken())
    //                .contentType(ContentType.JSON)
    //                .when()
    //                .patch("{id}",employeeId).body().as(Employee.class);
    //    }

}
