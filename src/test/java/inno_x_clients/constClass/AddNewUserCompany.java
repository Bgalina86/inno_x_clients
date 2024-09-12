package inno_x_clients.constClass;

import inno_x_clients.x_clients.model.CreateEmployeeReqest;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;


public class AddNewUserCompany implements ArgumentsProvider {
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        CreateEmployeeReqest createEmployeeReqest1 = new CreateEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        CreateEmployeeReqest createEmployeeReqest2 = new CreateEmployeeReqest("", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        CreateEmployeeReqest createEmployeeReqest3 = new CreateEmployeeReqest("Kolay", "",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        CreateEmployeeReqest createEmployeeReqest4 = new CreateEmployeeReqest("Kolay", "Ivanov",
            "", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        CreateEmployeeReqest createEmployeeReqest5 = new CreateEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "", "privet.ru", "+7987884555", "15.06.2000", true
        );
        CreateEmployeeReqest createEmployeeReqest6 = new CreateEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "", "+7987884555", "15.06.2000", true
        ); CreateEmployeeReqest createEmployeeReqest7 = new CreateEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "", "15.06.2000", true
        ); CreateEmployeeReqest createEmployeeReqest8 = new CreateEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "", true
        );
             return Stream.of(
            Arguments.of(createEmployeeReqest1),
                 Arguments.of(createEmployeeReqest2),
                 Arguments.of(createEmployeeReqest3),
                 Arguments.of(createEmployeeReqest4),
                 Arguments.of(createEmployeeReqest5),
                 Arguments.of(createEmployeeReqest6),
                 Arguments.of(createEmployeeReqest7),
                 Arguments.of(createEmployeeReqest8));
    }

}
