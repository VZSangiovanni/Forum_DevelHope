package co.develhope.forum.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private int id;
    private String userName;
    private String userPassword;
    private Long userCreation;
    private String userActivationCode; //= UUID.randomUUID().toString();
    private Boolean isActive;// = false;
    private String userEmail;
    private String userFirstName;
    private String userLastName;

    private List<String> userRoles;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Long getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(Long userCreation) {
        this.userCreation = userCreation;
    }

    public String getUserActivationCode() {
        return userActivationCode;
    }

    public void setUserActivationCode(String userActivationCode) {
        this.userActivationCode = userActivationCode;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userCreation=" + userCreation +
                ", userActivationCode='" + userActivationCode + '\'' +
                ", isActive=" + isActive +
                ", userEmail='" + userEmail + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public void grantAuthority(String role){
        if (userRoles == null) userRoles = new ArrayList<>();
        this.userRoles.add(role);
    }

    public void grantAuthorities(List<String> roles) {
        this.userRoles = roles;
    }

    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }
}
