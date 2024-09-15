package inno_x_clients.constClass;

import inno_x_clients.x_clients.model.PostEmployeeRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;


public class AddNewUserCompany implements ArgumentsProvider {

    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        PostEmployeeRequest postEmployeeRequest1 = new PostEmployeeRequest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest2 = new PostEmployeeRequest("", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest3 = new PostEmployeeRequest("Kolay", "",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest4 = new PostEmployeeRequest("Kolay", "Ivanov",
            "", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest5 = new PostEmployeeRequest("Kolay", "Ivanov",
            "Ivanovich", 30, "", "privet.ru", "+7987884555", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest6 = new PostEmployeeRequest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "", "+7987884555", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest7 = new PostEmployeeRequest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "", "15.06.2000", true
        );
        PostEmployeeRequest postEmployeeRequest8 = new PostEmployeeRequest("Kolay", "Ivanov",
            "Ivanovich", 30, "ddbgfb@mail.com", "privet.ru", "+7987884555", "", true
        );
        return Stream.of(
            Arguments.of(postEmployeeRequest1),
            Arguments.of(postEmployeeRequest2),
            Arguments.of(postEmployeeRequest3),
            Arguments.of(postEmployeeRequest4),
            Arguments.of(postEmployeeRequest5),
            Arguments.of(postEmployeeRequest6),
            Arguments.of(postEmployeeRequest7),
            Arguments.of(postEmployeeRequest8));
    }

}
