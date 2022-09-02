package co.develhope.forum.dao;


import co.develhope.forum.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//All'interno dei DAO vanno i metodi che fanno le query

@Repository
public class UserDAO {

    //questo serve a fare le query
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);


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

    public boolean createUser(UserModel userModel){
        int count = 0;
        try {
            count+= jdbcTemplate.update("insert into user_data (User_First_Name, User_Last_Name, User_Email) values (?, ?, ?)",
                    new Object[]{userModel.getUserFirstName(),userModel.getUserLastName(), userModel.getUserEmail()});
            if (count==1){
                Integer idUserData = jdbcTemplate.queryForObject("SELECT id_User_Data FROM user_data WHERE User_Email = ?",
                        Integer.class, new Object[]{userModel.getUserEmail()});

                count+= jdbcTemplate.update("insert into `user` (User_Name, User_Password, User_Creation, User_Data_id_User_Data) values (?, ?, ?, ?)",
                        new Object[]{userModel.getUserName(), userModel.getUserPassword(), System.currentTimeMillis(), idUserData});
            }

            return count == 2;
        } catch (Exception e){
            log.error("ERROR", e);
            return false;
        }
    }


}
