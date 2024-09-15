package inno_x_clients.x_clients.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Employee(int id,
                       String firstName,
                       String lastName,
                       String middleName,
                       int companyId,
                       String email,
                       @JsonProperty("url")
                       @JsonAlias({"url", "avatar_url"}) String url,
                       String phone,
                       String birthdate,
                       boolean isActive,
                       @JsonIgnore String createDateTime,
                       @JsonIgnore String lastChangedDateTime) {


    public Employee(int id, String firstName, String lastName, String middleName, int companyId,
        String email, String url, String phone, String birthdate, boolean isActive,
        String createDateTime, String lastChangedDateTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyId = companyId;
        this.email = email;
        this.url = url;
        this.phone = phone;
        this.birthdate = birthdate;
        this.isActive = isActive;
        this.createDateTime = null;
        this.lastChangedDateTime = null;

    }


    @Override
    public int id() {
        return id;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String middleName() {
        return middleName;
    }

    @Override
    public int companyId() {
        return companyId;
    }

    public String email() {
        return email;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public String birthdate() {
        return birthdate;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

}
