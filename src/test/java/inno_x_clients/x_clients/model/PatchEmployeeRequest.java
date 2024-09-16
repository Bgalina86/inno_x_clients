package inno_x_clients.x_clients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PatchEmployeeRequest(String lastName, String email, String url, String phone,
                                   boolean isActive) {

}
