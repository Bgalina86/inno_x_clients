package inno_x_clients.x_clients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Employee(int id, String firstName, String lastName, String middleName, int companyId,
                       String EMAIL, String url, String phone,
                       String birthdate, boolean isActive, String s, String s1) {


    public Employee(int id, String firstName, String lastName, String middleName, int companyId,
        String EMAIL, String url, String phone, String birthdate, boolean isActive,
        String s, String s1) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyId = companyId;
        this.EMAIL = EMAIL;
        this.url = url;
        this.phone = phone;
        this.birthdate = birthdate;
        this.isActive = isActive;
        this.s = null;
        this.s1 = null;

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

    @Override
    public String EMAIL() {
        return EMAIL;
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
