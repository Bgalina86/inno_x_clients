package inno_x_clients.x_clients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateEmployeeReqest(String firstName, String lastName, String middleName, int companyId, String EMAIL, String url, String phone,
                                   String birthdate, boolean isActive) {

}
