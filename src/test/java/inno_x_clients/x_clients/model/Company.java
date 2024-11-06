package inno_x_clients.x_clients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inno_x_clients.x_clients.helper.CompanyApiHelper;
import inno_x_clients.x_clients.helper.ConfProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

// Вариант #2 решения проблемы с неизвестными полями в JSON.
// Поставить над классом аннотацию @JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Company(int id, String name, String description, boolean isActive) {
    private static ConfProperties properties;
    private static String username;
    private static String password;
    static CompanyApiHelper helper;

    public static void setUp() {
        properties = new ConfProperties();
        RestAssured.baseURI = properties.getProperty("baseURI");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
    }
    public void setUpL() {

        helper = new CompanyApiHelper();
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


