package co.develhope.forum.model;



import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;


import java.util.List;


public class User extends UserDetails {

    private int id;

    private String resetPasswordCode;
    private Long userCreation;
    private String userActivationCode;
    private Boolean isActive;
    private String userEmail;
    private String userFirstName;
    private String userLastName;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
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
                ", userName='" + super.getUsername() + '\'' +
                ", userPassword='" + super.getPassword() + '\'' +
                ", userCreation=" + userCreation +
                ", userActivationCode='" + userActivationCode + '\'' +
                ", isActive=" + isActive +
                ", userEmail='" + userEmail + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userRoles=" + super.getRoles() +
                '}';
    }





}
