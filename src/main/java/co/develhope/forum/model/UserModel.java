package co.develhope.forum.model;

import lombok.Data;

@Data
public class UserModel {

    private int id;
    private String userName;
    private String userPassword;
    private Long userCreation;
    private String userEmail;




}
