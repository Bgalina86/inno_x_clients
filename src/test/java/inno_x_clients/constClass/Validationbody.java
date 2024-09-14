package inno_x_clients.constClass;

import inno_x_clients.x_clients.model.CreateEmployeeReqest;
import inno_x_clients.x_clients.model.PatchEmployeeReqest;

public class Validationbody {


    public static  final PatchEmployeeReqest body = new PatchEmployeeReqest("Kolay", "Ivanov",
            "Ivanovich", 581, "ddbgfb@mail.com", "privet.ru", "+7987884555", "2024-09-12T05:20:22.243Z", true);
    public static  final CreateEmployeeReqest bodyCreate = new CreateEmployeeReqest("Petrov",
       "ddbgfb123@mail.com", "privet123.ru", "+79878844545",  false);



}
