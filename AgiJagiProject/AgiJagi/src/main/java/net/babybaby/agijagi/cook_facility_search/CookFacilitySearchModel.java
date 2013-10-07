package net.babybaby.agijagi.cook_facility_search;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class CookFacilitySearchModel {

    private String name;
    private String Certification_no;
    private String location;
    private String telephone;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertification_no() {
        return Certification_no;
    }

    public void setCertification_no(String certification_no) {
        Certification_no = certification_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
