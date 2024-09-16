package inno_x_clients.x_clients;

import static org.junit.jupiter.api.Assertions.assertFalse;

import inno_x_clients.x_clients.ext.ApiHelperParameterResolver;
import inno_x_clients.x_clients.ext.NewCompanyParameterResolver;
import inno_x_clients.x_clients.helper.CompanyApiHelper;
import inno_x_clients.x_clients.model.Company;
import inno_x_clients.x_clients.model.CreateCompanyResponse;
import io.restassured.RestAssured;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith({ApiHelperParameterResolver.class, NewCompanyParameterResolver.class})
public class CompanyBusinessTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://x-clients-be.onrender.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void iCanDeleteCompany(CompanyApiHelper helper, CreateCompanyResponse newCompany)
        throws InterruptedException {
        helper.deleteCompany(newCompany.id());
        Optional<Company> optional = helper.getById(3376);
        assertFalse(optional.isPresent());
    }
}
