package inno_x_clients.x_clients;

import static inno_x_clients.x_clients.helper.EmployeeRandomeService.generateEmployee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javafaker.Faker;
import inno_x_clients.x_clients.ext.DatabaseService;
import inno_x_clients.x_clients.ext.DbProperties;
import inno_x_clients.x_clients.helper.CompanyApiHelper;
import inno_x_clients.x_clients.helper.ConfProperties;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
//import inno_x_clients.x_clients.model.CreateEmployeeResponse;
import inno_x_clients.x_clients.model.Employee;
import io.restassured.RestAssured;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import io.restassured.parsing.Parser;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import inno_x_clients.x_clients.model.*;


public class EmployeeBusinessTestBD {

    private EmployeeApiHelper employeeApiHelper;
    private CompanyApiHelper companyApiHelper;
    private DatabaseService databaseService;
    private int companyId;
    private int employeeId;

    private  String username;
    private String password;
    private int id;

    @BeforeEach
    public  void setUp() throws SQLException, IOException {

        RestAssured.baseURI = DbProperties.getProperties("baseURI");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.defaultParser = Parser.JSON;

        databaseService = new DatabaseService();
        databaseService.connectToDb();

        companyId = databaseService.createNewCompany();
        employeeId = databaseService.createNewEmployee(companyId);

        databaseService.createNewEmployee(companyId);

        companyApiHelper = new CompanyApiHelper();
        employeeApiHelper = new EmployeeApiHelper();

        var properties = new ConfProperties();
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }


    @AfterEach
    public  void tearDown() throws SQLException {
        databaseService.deleteCompanyAndItsEmloyees(companyId);
        databaseService.closeConnection();
    }


    @Test
    @Description("Могу создать нового пользователя")
    public void ICanAddNewEmployee() throws SQLException, InterruptedException {
        Faker faker = new Faker();
        Employee employee = generateEmployee(faker, companyId);

        CreateEmployeeResponse response = employeeApiHelper.createEmployee(employee);

        ResultSet resultSet = databaseService.getEmployeeInfo(response.id());
        resultSet.next();
        assertEquals(response.id(), resultSet.getInt(1));
        assertEquals(employee.firstName(), resultSet.getString(2));
        assertEquals(employee.lastName(), resultSet.getString(3));
        assertEquals(employee.middleName(), resultSet.getString(4));
        assertEquals(employee.email(), resultSet.getString(6));
        assertEquals(employee.url(), resultSet.getString(7));
        assertEquals(employee.phone(), resultSet.getString(8));
        assertEquals(employee.birthdate().toString(), resultSet.getDate(9).toString());
        assertEquals(employee.isActive(), resultSet.getBoolean(10));
    }

    @Test
    @Description("Могу получить информацию о пользователе")
    public void ICanGetEmployeeInfo() {
        Employee employee = employeeApiHelper.getEmployeeInfo(employeeId);
        assertEquals(employee.id(), employeeId);
        assertNotNull(employee.lastName());
        assertNotNull(employee.firstName());
    }


    @Test
    @Description("Могу получить список сотрудников по компании")
    public void ICanGetEmployeeListByCompany() {
        List<Employee> employeeList = employeeApiHelper.getListOfEmployee(companyId);
        assertTrue(employeeList.size() > 0);
    }


    @Test
    @Description("Могу редактировать пользователя")
    public void ICanEditEmployee() throws IOException {
        Faker faker = new Faker();

        PatchEmployeeRequest patchEmployeeRequest = new PatchEmployeeRequest
                (faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.internet().url(),
                        faker.phoneNumber().phoneNumber(),
                        faker.bool().bool());

        Employee employee = employeeApiHelper.editEmployee(employeeId, patchEmployeeRequest);
        assertEquals(employee.isActive(),patchEmployeeRequest.isActive());
        assertEquals(employee.lastName(),patchEmployeeRequest.lastName());
        assertEquals(employee.email(),patchEmployeeRequest.email());
        assertEquals(employee.url(),patchEmployeeRequest.url());
        assertEquals(employee.phone(),patchEmployeeRequest.phone());
        assertEquals(employee.isActive(),patchEmployeeRequest.isActive());
    }
}
