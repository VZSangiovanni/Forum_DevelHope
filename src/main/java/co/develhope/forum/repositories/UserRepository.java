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

    private User findById(int id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id_user = ?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public User findByName(String username) {

        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user,user_data WHERE User_Name=? AND user.User_Data_id_User_Data=id_User_Data",
                    new UserRowMapper(), username);
            int userModelID = jdbcTemplate.queryForObject("SELECT id_User FROM `user` WHERE User_Name = ?",
                    Integer.class, new Object[]{user.getUsername()});
            user.setId(userModelID);
            boolean isActive = jdbcTemplate.queryForObject("SELECT isActive FROM user WHERE User_Name = ?",
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
            User user = jdbcTemplate.queryForObject("SELECT * FROM user,user_data WHERE User_ActivationCode=?",
                    new UserRowMapper(), activationCode);

            int userModelID = jdbcTemplate.queryForObject("SELECT id_User FROM `user` WHERE User_Name = ?",
                    Integer.class, new Object[]{user.getUsername()});
            user.setId(userModelID);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<String> getUserRoles(String userName) {

        String querySQL = "SELECT User_Roles_id_User_Roles FROM user WHERE User_Name = ?";
        //dataSource.
        List<String> userRoles = jdbcTemplate.queryForList(querySQL, String.class, userName);
        return userRoles;
    }

    public List<User> users(String userName) {
        String query = "SELECT * FROM user AS u WHERE u.user_name = ?", username;
        List<User> usersList = jdbcTemplate.queryForList(query, User.class, userName);
        return usersList;
    }
    public List<Map<String, Object>> getUserTopics(String userId) {
        String querySQL = "SELECT * FROM forum_topic where User_id_User =?";
        List<Map<String, Object>> userTopics= jdbcTemplate.queryForList(querySQL,new TopicRowMapper(),userId);
        return userTopics;
    }

    public Topic findByCategory() {
        try {
            Topic topic = jdbcTemplate.queryForObject("SELECT * FROM forum_category f INNER JOIN forum_topic ft ON t.id_Forum_Category=fc.Forum_Category_id_Forum_Category WHERE t.forum_category_id_Forum_Category=?",
                    new TopicRowMapper() );
            int topicModelID = jdbcTemplate.queryForObject("SELECT id_Forum_Category FROM `forum_category` WHERE id_Forum_Category =?",
                    Integer.class, new Object[]{topic});
            topic.setIdCategory(topicModelID);
            return topic;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

