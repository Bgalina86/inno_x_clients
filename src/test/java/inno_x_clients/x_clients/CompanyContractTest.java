package inno_x_clients.x_clients;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import inno_x_clients.x_clients.helper.CompanyApiHelper;
import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.model.AuthResponse;
import inno_x_clients.x_clients.model.CreateCompanyRequest;
import inno_x_clients.x_clients.model.CreateCompanyResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CompanyContractTest {

    private static ConfProperties properties;
    private static String username;
    private static String password;
    CompanyApiHelper helper;

    @BeforeEach
    public void setUpL() {

        helper = new CompanyApiHelper();
    }

    @BeforeAll
    public static void setUp() {
        properties = new ConfProperties();
        RestAssured.baseURI = properties.getProperty("baseURI");
       username = properties.getProperty("username");
       password = properties.getProperty("password");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void iCanCreateNewCompany() {
        AuthResponse info = helper.auth(username, password);

        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest("TecnaSchool",
            "Онлайн-курсы");

        given()
            .basePath("company")
            .body(createCompanyRequest)
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
    public void getCompany() {
        helper.printCompanyInfo(34);
    }

    @Test
    public void iCanDeleteCompany() {

        CreateCompanyResponse response = (CreateCompanyResponse) helper.createCompany("TecnaSchool",
            "Онлайн-курсы");
        Response r = helper.deleteCompany(response.id());

        r.then().statusCode(200);
    }
    @Test
    public void getCompanyIdNewCompany(){
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
    }
}
