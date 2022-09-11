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

    private Role findByType(String roleType) {
        try {
            Role role = jdbcTemplate.queryForObject("SELECT * FROM user_roles WHERE Roles_Type = ?",
                    BeanPropertyRowMapper.newInstance(Role.class), roleType);
            return role;
        }catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}
