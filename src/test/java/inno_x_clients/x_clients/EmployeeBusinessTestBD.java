package inno_x_clients.x_clients;

import static inno_x_clients.x_clients.helper.EmployeeRandomeService.generateEmployee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.inno.x_employees.helper.EmployeeService.generateEmployee;

import com.github.javafaker.Faker;
import inno_x_clients.x_clients.ext.DatabaseService;
import inno_x_clients.x_clients.ext.EnvProperties;
import inno_x_clients.x_clients.helper.CompanyApiHelper;
import inno_x_clients.x_clients.helper.EmployeeApiHelper;
import inno_x_clients.x_clients.model.CreateEmployeeResponse;
import inno_x_clients.x_clients.model.Employee;
import io.restassured.RestAssured;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.inno.x_employees.model.*;

public class EmployeeBusinessTestBD {

    static EmployeeApiHelper employeeHelper;
    static CompanyApiHelper companyHelper;
    static DatabaseService databaseService;
    static int companyId;
    static int employeeId;


    @BeforeAll
    public static void setUp() throws SQLException, IOException {

        RestAssured.baseURI = EnvProperties.getEnvProperties("url");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        databaseService = new DatabaseService();
        databaseService.connectToDb();
        companyId = databaseService.createNewCompany();
        employeeId = databaseService.createNewEmployee(companyId);
        databaseService.createNewEmployee(companyId);

        companyHelper = new CompanyApiHelper();
        employeeHelper = new EmployeeApiHelper();

    }


    @AfterAll
    public static void tearDown() throws SQLException {
        databaseService.deleteCompanyAndItsEmloyees(companyId);
        databaseService.closeConnection();
    }


    @Test
    @Description("Проверка, что могу создать нового пользователя")
    public void ICanAddNewEmployee() throws SQLException, InterruptedException {
        Faker faker = new Faker();
        Employee employee = generateEmployee(faker, companyId);

        CreateEmployeeResponse response = employeeHelper.createEmployee(employee);

        ResultSet resultSet = databaseService.getEmployeeInfo(response.id());
        resultSet.next();
        assertEquals(response.id(), resultSet.getInt(1));
        assertEquals(employee.firstName(), resultSet.getString(2));
        assertEquals(employee.lastName(), resultSet.getString(3));
        assertEquals(employee.middleName(), resultSet.getString(4));
        assertEquals(employee.email(), resultSet.getString(6));
        assertEquals(employee.avatar_url(), resultSet.getString(7));
        assertEquals(employee.phone(), resultSet.getString(8));
        assertEquals(employee.birthdate().toString(), resultSet.getDate(9).toString());
        assertEquals(employee.isActive(), resultSet.getBoolean(10));

    }

    @Test
    @Description("Проверка, что могу получить информацию о пользователе")
    public void ICanGetEmployeeInfo() throws IOException {
        Employee employee = employeeHelper.getEmployeeInfo(employeeId);
        assertEquals(employee.id(), employeeId);
        assertNotNull(employee.lastName());
        assertNotNull(employee.firstName());
    }


    @Test
    @Description("Проверка, что я могу получить список сотрудников по компании")
    public void ICanGetEmployeeListByCompany() throws IOException {
        List<Employee> employeeList = employeeHelper.getListOfEmployee(companyId);
        assertTrue(employeeList.size() > 0);
    }


    @Test
    public void ICanEditEmployee() throws IOException {
        Faker faker = new Faker();

        PatchEmployeeRequest patchEmployeeRequest = new PatchEmployeeRequest
                (faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.internet().url(),
                        faker.phoneNumber().phoneNumber(),
                        faker.bool().bool());

        Employee employee = employeeHelper.editEmployee(employeeId, patchEmployeeRequest);

        assertEquals(employee.lastName(),patchEmployeeRequest.lastName());
        assertEquals(employee.email(),patchEmployeeRequest.email());
        assertEquals(employee.avatar_url(),patchEmployeeRequest.url());
        assertEquals(employee.phone(),patchEmployeeRequest.phone());
        assertEquals(employee.isActive(),patchEmployeeRequest.isActive());
    }

   }
