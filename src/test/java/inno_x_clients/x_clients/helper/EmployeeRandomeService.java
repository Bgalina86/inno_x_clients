package inno_x_clients.x_clients.helper;


import com.github.javafaker.Faker;
import inno_x_clients.x_clients.model.Employee;
import java.text.SimpleDateFormat;

public class EmployeeRandomeService {

    public static Employee generateEmployee(Faker faker, int id) {

        return new Employee(
            0,
            faker.name().firstName(),
            faker.name().lastName(),
            faker.name().suffix(),
            id,
            faker.internet().emailAddress(),
            faker.internet().url(),
            faker.phoneNumber().cellPhone(),
            new SimpleDateFormat("yyyy-MM-dd").format(faker.date().birthday(35, 55)),
            true,
            "2024-09-13 01:03:20.608 +0500",
            "2024-09-13 01:03:20.608 +0500"
        );
    }
}
