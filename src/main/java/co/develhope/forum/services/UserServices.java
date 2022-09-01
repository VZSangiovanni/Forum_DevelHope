package co.develhope.forum.services;


import co.develhope.forum.controllers.dto.response.BaseResponse;
import co.develhope.forum.dao.UserDAO;
import co.develhope.forum.exception.UserEmailAlreadyExistException;
import co.develhope.forum.exception.UserNameAlreadyExistException;
import co.develhope.forum.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServices {

    //Logica Applicativa

    @Autowired
    UserDAO userDAO;

    public BaseResponse checkedUserName(String userName){
        if(userDAO.checkUserNameExist(userName)){
            throw new UserNameAlreadyExistException(userName);
        }else {
            return new BaseResponse("User Name available");
        }

    }

    public BaseResponse checkedUserEmail(String userEmail){
        if(userDAO.checkUserEmailExist(userEmail)){
            throw new UserEmailAlreadyExistException(userEmail);
        }else {
            return new BaseResponse("User Email available");
        }

    }

    public boolean createUser(UserModel userModel){
        try {
            //TODO add checkedUserName and checkedUserEmail
            //TODO integrate mail service
            return userDAO.createUser(userModel);
        } catch (Exception e){
            return false;
        }
    }


}
