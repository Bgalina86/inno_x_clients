package inno_x_clients.constClass;

import inno_x_clients.x_clients.model.PatchEmployeeReqest;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;


public class AddNewUserCompany implements ArgumentsProvider {
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        PatchEmployeeReqest patchEmployeeReqest1 = new PatchEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PatchEmployeeReqest patchEmployeeReqest2 = new PatchEmployeeReqest("", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PatchEmployeeReqest patchEmployeeReqest3 = new PatchEmployeeReqest("Kolay", "",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PatchEmployeeReqest patchEmployeeReqest4 = new PatchEmployeeReqest("Kolay", "Ivanov",
            "", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PatchEmployeeReqest patchEmployeeReqest5 = new PatchEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PatchEmployeeReqest patchEmployeeReqest6 = new PatchEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "", "+7987884555", "15.06.2000", true
        ); PatchEmployeeReqest patchEmployeeReqest7 = new PatchEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "", "15.06.2000", true
        ); PatchEmployeeReqest patchEmployeeReqest8 = new PatchEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "", true
        );
             return Stream.of(
            Arguments.of(patchEmployeeReqest1),
                 Arguments.of(patchEmployeeReqest2),
                 Arguments.of(patchEmployeeReqest3),
                 Arguments.of(patchEmployeeReqest4),
                 Arguments.of(patchEmployeeReqest5),
                 Arguments.of(patchEmployeeReqest6),
                 Arguments.of(patchEmployeeReqest7),
                 Arguments.of(patchEmployeeReqest8));
    }

}
