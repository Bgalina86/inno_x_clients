package inno_x_clients.constClass;

import inno_x_clients.x_clients.model.CreateEmployeeReqest;

public class Validationbody {
    public static final String titleScriptTag = "<script>alert(?XSS?)</script>";
    public static  final CreateEmployeeReqest body = new CreateEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 581, "ddbgfb@mail.com", "privet.ru", "+7987884555", "2024-09-12T05:20:22.243Z", true);


}
