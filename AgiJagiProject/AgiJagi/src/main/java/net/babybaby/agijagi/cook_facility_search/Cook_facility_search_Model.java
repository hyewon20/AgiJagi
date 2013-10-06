package net.babybaby.agijagi.cook_facility_search;

/**
 * Created by FlaShilver on 2013. 10. 5..
 */
public class Cook_facility_search_Model {
    String cook_name;
    String cook_license_num;
    String cook_address;
    String cook_phone_num;

    String facility_name;
    String facility_description;
    String facility_owner_name;
    String facility_phone_num;

    public Cook_facility_search_Model() {

    }

    public Cook_facility_search_Model(String cook_name, String cook_address) {
        this.cook_name = cook_name;
        this.cook_address = cook_address;
    }

    public String getCook_name() {
        return cook_name;
    }

    public void setCook_name(String cook_name) {
        this.cook_name = cook_name;
    }

    public String getCook_license_num() {
        return cook_license_num;
    }

    public void setCook_license_num(String cook_license_num) {
        this.cook_license_num = cook_license_num;
    }

    public String getCook_address() {
        return cook_address;
    }

    public void setCook_address(String cook_address) {
        this.cook_address = cook_address;
    }

    public String getCook_phone_num() {
        return cook_phone_num;
    }

    public void setCook_phone_num(String cook_phone_num) {
        this.cook_phone_num = cook_phone_num;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getFacility_description() {
        return facility_description;
    }

    public void setFacility_description(String facility_description) {
        this.facility_description = facility_description;
    }

    public String getFacility_owner_name() {
        return facility_owner_name;
    }

    public void setFacility_owner_name(String facility_owner_name) {
        this.facility_owner_name = facility_owner_name;
    }

    public String getFacility_phone_num() {
        return facility_phone_num;
    }

    public void setFacility_phone_num(String facility_phone_num) {
        this.facility_phone_num = facility_phone_num;
    }
}
