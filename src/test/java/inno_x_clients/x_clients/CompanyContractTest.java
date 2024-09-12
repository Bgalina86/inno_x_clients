package inno_x_clients.x_clients;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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

    CompanyApiHelper helper;
    @BeforeEach
    public void setUpL() {

        helper = new CompanyApiHelper();
    }
    
    @BeforeAll
    public static void setUp() {
        properties = new ConfProperties();
        RestAssured.baseURI = properties.getProperty("baseURI");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

 
    
    @Test
    public void status200OnGetCompanies() {
        given()
                .header("ABC", "123")
                .basePath("company")
                .when()
                .get()
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    public void iCanAuth() {
        String body = """
                {
                      "username": "tecna",
                      "password": "tecna-fairy"
                }
                """;
        given()
                .basePath("/auth/login")
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("userToken", is(not((blankString()))));
    }

    @Test
    public void iCanCreateNewCompany() {
        AuthResponse info = helper.auth(properties.getProperty("username"), properties.getProperty("password"));

        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest("TecnaSchool", "Онлайн-курсы");

        RestAssured.given()
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


        CreateCompanyResponse response = (CreateCompanyResponse) helper.createCompany("TecnaSchool", "Онлайн-курсы");
        Response r = helper.deleteCompany(response.id());

        r.then().statusCode(200);

    }
}
