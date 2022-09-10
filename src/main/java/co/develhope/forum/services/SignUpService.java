package co.develhope.forum.services;


import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dao.SignUpDAO;
import co.develhope.forum.dto.response.UserDTO;
import co.develhope.forum.exception.UserEmailAlreadyExistException;
import co.develhope.forum.exception.UserNameAlreadyExistException;
import co.develhope.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SignUpService {

    //Logica Applicativa

    @Autowired
    private SignUpDAO signUpDAO;

    @Autowired
    private NotificationService notificationService;

    public BaseResponse checkedUserName(String userName){
        if(signUpDAO.checkUserNameExist(userName)){
            throw new UserNameAlreadyExistException(userName);
        }else {
            return new BaseResponse("User Name available");
        }

    }

    public BaseResponse checkedUserEmail(String userEmail){
        if(signUpDAO.checkUserEmailExist(userEmail)){
            throw new UserEmailAlreadyExistException(userEmail);
        }else {
            return new BaseResponse("User Email available");
        }

    }

    public BaseResponse createUser(User user){
        if(signUpDAO.checkUserNameExist(user.getUserName())){
            throw new UserNameAlreadyExistException(user.getUserName());
        } else if (signUpDAO.checkUserEmailExist(user.getUserEmail())) {
            throw new UserEmailAlreadyExistException(user.getUserEmail());
        }else {
            //notificationService.sendActivationEmail(userModel); Disable for Test
            signUpDAO.createUser(user);
            return new UserDTO(user.getId(), user.getUserName());
        }

    }


}
