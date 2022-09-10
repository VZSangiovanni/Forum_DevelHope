package co.develhope.forum.repositories;

import co.develhope.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE User_Name=?",
                    BeanPropertyRowMapper.newInstance(User.class), username);
           // user.grantAuthorities(this.getUserRoles(user.getUserName()));
            return user;

        } catch (IncorrectResultSizeDataAccessException e) {

            return null;
        }
    }

    private List<String> getUserRoles(String userName) {

        String querySQL = "";
        //dataSource.
        List<String> userRoles = jdbcTemplate.queryForList(querySQL, String.class);
        return userRoles;
    }

}
