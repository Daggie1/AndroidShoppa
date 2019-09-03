package tech.muva.academy.android_shoppa.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {

//    @SerializedName("username")
//    @Expose
    private String username;
//    @SerializedName("email")
//    @Expose
    private String email;
//    @SerializedName("first_name")
//    @Expose
    private String first_name;
//    @SerializedName("last_name")
//    @Expose
    private String last_name;
//    @SerializedName("phone_number")
//    @Expose
    private String phone_number;

//    @SerializedName("password")
//    @Expose
    private String password;

    public RegisterModel(String username, String first_name, String last_name, String password, String email, String phone_number) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
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

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}