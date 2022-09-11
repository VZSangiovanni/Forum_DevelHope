package co.develhope.forum.dao;


import co.develhope.forum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//All'interno dei DAO vanno i metodi che fanno le query

@Repository
public class SignUpDAO {

    //questo serve a fare le query
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(SignUpDAO.class);


    public boolean checkUserNameExist(String userName){
      try {
          return jdbcTemplate.queryForObject("SELECT 1 FROM user WHERE User_Name = ?", Integer.class, userName) != null;
      }catch (Exception e){
         return false;
      }

    }
    public boolean checkUserEmailExist(String userEmail){
        try {
            return jdbcTemplate.queryForObject("SELECT 1 FROM user_data WHERE User_Email = ?", Integer.class, userEmail) != null;
        }catch (Exception e){
            return false;
        }

    }

    public boolean createUser(User user){
        int count = 0;
        try {
            count+= jdbcTemplate.update("insert into user_data (User_First_Name, User_Last_Name, User_Email) values (?, ?, ?)",
                    new Object[]{user.getUserFirstName(), user.getUserLastName(), user.getUserEmail()});
            if (count==1){
                Integer idUserData = jdbcTemplate.queryForObject("SELECT id_User_Data FROM user_data WHERE User_Email = ?",
                        Integer.class, new Object[]{user.getUserEmail()});

                count+= jdbcTemplate.update("insert into `user` (User_Name, User_Password, User_Creation, User_ActivationCode, isActive, User_Data_id_User_Data) values (?, ?, ?, ?, ?, ?)",
                        new Object[]{user.getUserName(), passwordEncoder.encode(user.getUserPassword()), System.currentTimeMillis(),user.getUserActivationCode(), user.getActive(), idUserData});

                int userModelID = jdbcTemplate.queryForObject("SELECT id_User FROM `user` WHERE User_Name = ?",
                        Integer.class, new Object[]{user.getUserName()});
                user.setId(userModelID);
            }
            return count == 2;
        } catch (Exception e){
            log.error("ERROR", e);
            return false;
        }
    }

        public void activate(Integer id, String code, boolean active) {
        String SQL1 = "UPDATE user SET User_ActivationCode = ? WHERE id_User = ?";
        jdbcTemplate.update(SQL1, code, id);
        String SQL2 = "UPDATE user SET isActive = ? WHERE id_User = ?";
        jdbcTemplate.update(SQL2, active, id);
        }

        public void getRole(Integer id) {
        String SQL = "Update user SET User_Roles_id_User_Roles = 1 WHERE id_User = ?";
        jdbcTemplate.update(SQL, id);
        }

}
