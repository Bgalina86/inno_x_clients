package inno_x_clients.x_clients.helper;

import static io.restassured.RestAssured.given;

import inno_x_clients.x_clients.model.AuthRequest;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.Company;
import inno_x_clients.x_clients.model.CreateCompanyRequest;
import inno_x_clients.x_clients.model.CreateCompanyResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Optional;


public class CompanyApiHelper {
    private static ConfProperties properties;
    private String username;
   private String password;
    CompanyApiHelper helper;

    public void setUp() {
        properties = new ConfProperties();
        RestAssured.baseURI = properties.getProperty("baseURI");
       username = properties.getProperty("username");
       password = properties.getProperty("password");
    }
    public void setUpL() {

        helper = new CompanyApiHelper();
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

    public void printCompanyInfo(int id) {
        given()
            .basePath("company")
            .when()
            .get("{companyId}", id)
            .body().prettyPrint();
    }

    public Object createCompany(String name, String descr) {
        AuthResponse info = auth(username, password);

        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest(name, descr);

        return given()
            .basePath("company")
            .body(createCompanyRequest)
            .header("x-client-token", info.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post().body().as(CreateCompanyResponse.class);
    }

    public Response deleteCompany(int id) {
        AuthResponse info = auth(username, password);

        return given()
            .basePath("company/delete")
            .header("x-client-token", info.userToken())
            .contentType(ContentType.JSON)
            .when()
            .get("{id}", id);

    }

    public Optional<Company> getById(int id) throws InterruptedException {
        Thread.sleep(3000);
        Response response = given()
            .basePath("company")
            .pathParam("id", id)
            .when().get("/{id}");

        String header = response.header("Content-Length");
        if (header != null && header.equals("0")) {
            return Optional.empty();
        }

        Company company = response.as(Company.class);
        return Optional.of(company);
    }
    public int getCompanyIdNewCompany(){
        AuthResponse info = helper.auth(username, password);

        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest("TecnaSchool",
            "Онлайн-курсы");
        int companyId = RestAssured.given()
            .basePath("company")
            .body(createCompanyRequest)
            .header("x-client-token", info.userToken())
            .contentType(ContentType.JSON)
            .when()
            .post()
            .then().extract().response().jsonPath().getInt("id");
        System.out.println(companyId);
        return companyId;
    }
}
