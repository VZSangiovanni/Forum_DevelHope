package co.develhope.forum.repositories;

import co.develhope.forum.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role findByType(String roleType) {
        try {
            Role role = jdbcTemplate.queryForObject("SELECT * FROM user_roles WHERE Roles_Type = ?",
                    BeanPropertyRowMapper.newInstance(Role.class), roleType);
            return role;
        }catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public void updateModerator(String username) {
        String SQL = "UPDATE user SET User_Roles_id_User_Roles = 2 WHERE User_Name = ?";
        jdbcTemplate.update(SQL, username);
    }

    public void updateAdmin(String username) {
        String SQL = "UPDATE user SET User_Roles_id_User_Roles = 3 WHERE User_Name = ?";
        jdbcTemplate.update(SQL, username);
    }

    public void updateUser(String username) {
        String SQL = "UPDATE user SET User_Roles_id_User_Roles = 1 WHERE User_Name = ?";
        jdbcTemplate.update(SQL, username);
    }

}
