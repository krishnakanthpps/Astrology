package models;

public class Userdetailview {
    private Integer userId;
    private String username;
    private String email;
    private String mobile;
    private String first_name;
    private String last_name;
    private String dateofbirth;
    private String timeofbirth;
    private String placeofbirth;
    private String current_city;
    private String current_state;
    private String current_country;
    private String address;

    public Userdetailview(Integer userid, String username, String email, String mobile, String first_name, String last_name, String dateofbirth, String timeofbirth, String placeofbirth, String current_city, String current_state, String current_country, String address) {
        this.userId = userid;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dateofbirth = dateofbirth;
        this.timeofbirth = timeofbirth;
        this.placeofbirth = placeofbirth;
        this.current_city = current_city;
        this.current_state = current_state;
        this.current_country = current_country;
        this.address = address;
    }

    public Integer getUserid() {
        return userId;
    }

    public void setUserid(Integer userid) {
        this.userId = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getTimeofbirth() {
        return timeofbirth;
    }

    public void setTimeofbirth(String timeofbirth) {
        this.timeofbirth = timeofbirth;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(String current_city) {
        this.current_city = current_city;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getCurrent_country() {
        return current_country;
    }

    public void setCurrent_country(String current_country) {
        this.current_country = current_country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
