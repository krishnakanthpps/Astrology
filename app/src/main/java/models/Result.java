package models;

/**
 * Created by harsha on 5/30/2018.
 */

public class Result {
    private Integer userid;
    private String username;
    private String email;
    //private Integer roleId;
    private String first_name;
    //private Integer userdetailid;
    private String last_name;
    private String dateofbirth;
    private String timeofbirth;
    private String placeofbirth;
    private String current_city;
    private String current_state;
    private String current_country;
    private String mobile;
    private String dasa_at_birth;
    private String dasa_end_date;
    private String token;

    public Result(Integer userid, String username, String email, String first_name, String last_name, String dateofbirth, String timeofbirth, String placeofbirth, String current_city, String current_state, String current_country, String mobile, String dasa_at_birth, String dasa_end_date, String token) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        //this.roleId = roleId;
        this.first_name = first_name;
        //this.userdetailid = userdetailid;
        this.last_name = last_name;
        this.dateofbirth = dateofbirth;
        this.timeofbirth = timeofbirth;
        this.placeofbirth = placeofbirth;
        this.current_city = current_city;
        this.current_state = current_state;
        this.current_country = current_country;
        this.mobile = mobile;
        this.dasa_at_birth = dasa_at_birth;
        this.dasa_end_date = dasa_end_date;
        this.token = token;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

   /* public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }*/

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

   /* public Integer getUserdetailid() {
        return userdetailid;
    }

    public void setUserdetailid(Integer userdetailid) {
        this.userdetailid = userdetailid;
    }*/

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDasa_at_birth() {
        return dasa_at_birth;
    }

    public void setDasa_at_birth(String dasa_at_birth) {
        this.dasa_at_birth = dasa_at_birth;
    }

    public String getDasa_end_date() {
        return dasa_end_date;
    }

    public void setDasa_end_date(String dasa_end_date) {
        this.dasa_end_date = dasa_end_date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
