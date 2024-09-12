package inno_x_clients.x_clients.ext;

import inno_x_clients.x_clients.helper.CompanyApiHelper;
import inno_x_clients.x_clients.model.CreateCompanyResponse;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;


public class NewCompanyParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CreateCompanyResponse.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        CompanyApiHelper helper = new CompanyApiHelper();
        return helper.createCompany("Temp company", "Компания, которая будет удалена");
    }
}
