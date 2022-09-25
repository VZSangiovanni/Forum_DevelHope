package co.develhope.forum.repositories;

import co.develhope.forum.dao.rowmapper.UserRowMapper;
import co.develhope.forum.dto.response.UserDTO;
import co.develhope.forum.model.User;
import it.pasqualecavallo.studentsmaterial.authorization_framework.dao.UserDao;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

// inspired by https://www.devxperiences.com/pzwp1/2022/05/19/spring-boot-security-configuration-practically-explained-part2-jdbc-authentication/
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private User findById(int id){
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id_user = ?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
            return user;
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    public User findByName(String username) {

        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user u INNER JOIN user_data ud ON u.id_User=ud.User_id_User WHERE User_Name=?",
                    new UserRowMapper(), username);
            int userModelID = jdbcTemplate.queryForObject("SELECT id_User FROM `user` WHERE User_Name = ?",
                    Integer.class, new Object[]{user.getUsername()});
            user.setId(userModelID);
            boolean isActive= jdbcTemplate.queryForObject("SELECT isActive FROM user WHERE User_Name = ?",
                    boolean.class, username);
            user.setActive(isActive);
            List<String> userRoles = jdbcTemplate.queryForObject("SELECT Roles_Type FROM user,user_roles WHERE User_Name=? AND user.User_Roles_id_User_Roles = id_User_Roles", List.class, username);
            user.setRoles(userRoles);
            return user;

        } catch (IncorrectResultSizeDataAccessException e) {

            return null;
        }
    }

    public User findByActivationCode(String activationCode) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user u INNER JOIN user_data ud ON u.id_User=ud.User_id_User WHERE u.User_ActivationCode=?",
                    new UserRowMapper(), activationCode);

            int userModelID = jdbcTemplate.queryForObject("SELECT id_User FROM `user` WHERE User_Name = ?",
                    Integer.class, new Object[]{user.getUsername()});
            user.setId(userModelID);
            return user;
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    public List<String> getUserRoles(String userName) {
        String querySQL = "SELECT User_Roles_id_User_Roles FROM user WHERE User_Name = ?";
        //dataSource.
        List<String> userRoles = jdbcTemplate.queryForList(querySQL, String.class, userName);
        return userRoles;
    }

    /**
     * REPLACE INTO `IDM_USER_MEASURE`(`USER_ID`, `MEASURE_ID`, `MEASURE_DATE`, `MEASURE_STATUS`) VALUES(1, 1, "bla", "status updated");
     */

    public void updateUserEmailQuery(String username, String currentEmail) {
        String querySql = "INSERT OR REPLACE INTO (user_data) VALUES (?) FROM user WHERE 'User_Name' = ?";
        jdbcTemplate.update(querySql, currentEmail, username);
    }
}
//INSERT INTO user_data ('User_Email') VALUES (?) ON DUPLICATE KEY UPDATE 'User_Email' = VALUES ('User_Email'), FROM user WHERE 'User_Name' = ?
//UPDATE user_data,user SET User_Email = ? WHERE User_Name = ?