package inno_x_clients.constClass;

import inno_x_clients.x_clients.model.PatchEmployeeRequest;
import inno_x_clients.x_clients.model.PostEmployeeRequest;

public class Validationbody {


    public static final PostEmployeeRequest body = new PostEmployeeRequest("Kolay", "Ivanov",
        "Ivanovich", 581, "ddbgfb@mail.com", "privet.ru", "+7987884555", "2024-09-12T05:20:22.243Z",
        true);
    public static final PatchEmployeeRequest bodyCreate = new PatchEmployeeRequest("Petrov",
        "ddbgfb123@mail.com", "privet123.ru", "+79878844545", false);


}
