package co.develhope.forum.services;


import co.develhope.forum.controllers.dto.response.BaseResponse;
import co.develhope.forum.dao.UserDAO;
import co.develhope.forum.exception.UserEmailAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


    @Service
    public class UserServices {

        //Logica Applicativa

        @Autowired
        UserDAO userDAO;

        public BaseResponse checkedUserEmail(String userEmail){
            if(userDAO.checkUserEmailExist(userEmail)){
                throw new UserEmailAlreadyExistException(userEmail);
            }else {
                return new BaseResponse("User Email available");
            }

        }




    }
