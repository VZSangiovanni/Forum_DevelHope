package co.develhope.forum.dao.rowmapper;


import co.develhope.forum.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("User_Name"));
        user.setPassword(rs.getString("User_Password"));
        user.setResetPasswordCode(rs.getString("ResetPasswordCode"));
        user.setUserActivationCode(rs.getString("User_ActivationCode"));
        user.setUserEmail(rs.getString("User_Email"));
        user.setUserFirstName(rs.getString("User_First_Name"));
        user.setUserLastName(rs.getString("User_Last_Name"));
        user.setId(rs.getInt("id_User"));
        user.setUserCreation(rs.getLong("User_Creation"));
        user.setActive(rs.getBoolean("isActive"));
        return user;
    }
}