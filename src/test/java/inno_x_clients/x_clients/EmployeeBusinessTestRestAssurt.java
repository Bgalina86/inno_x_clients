package inno_x_clients.x_clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class EmployeeBusinessTestRestAssurt {

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
        int id = employeeApiHelper.postNewAddEmployee().id();
        assertNotNull(id);
    }

    @Test
    @DisplayName("Получение сотрудника по id")
    public void iCanGetUserCompany() {
        int idResponse = employeeApiHelper.postNewAddEmployee().id();
        int idRequest = employeeApiHelper.getEmployeeInfo(idResponse).id();
        assertEquals(idRequest, idResponse);
    }
}
