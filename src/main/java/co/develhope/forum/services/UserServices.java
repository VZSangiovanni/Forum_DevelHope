package co.develhope.forum.services;


import co.develhope.forum.controllers.dto.response.BaseResponse;
import co.develhope.forum.dao.UserDAO;
import co.develhope.forum.exception.UserNameAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServices {

    //Logica Applicativa

    @Autowired
    UserDAO userDAO;

    public boolean checkedUserEmail(String userEmail){
        return userDAO.checkUserEmailExist(userEmail);

    }




}
