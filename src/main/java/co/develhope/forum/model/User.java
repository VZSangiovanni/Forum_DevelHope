package co.develhope.forum.model;



import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;


import java.util.List;


public class User extends UserDetails {

    private int id;
    //private String userName;
    //private String userPassword;
    private Long userCreation;
    private String userActivationCode;
    private Boolean isActive;
    private String userEmail;
    private String userFirstName;
    private String userLastName;

    //private List<String> userRoles;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //public String getUserName() {
    //    return userName;
    //}

   // public void setUserName(String userName) {
       // this.userName = userName;
    //}

   // public String getUserPassword() {
        //return userPassword;
    //}

   // public void setUserPassword(String userPassword) {
       // this.userPassword = userPassword;
    //}

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

    /*public List<String> getUserRoles() {
        return userRoles;
    }*/

    /*public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }*/



}
