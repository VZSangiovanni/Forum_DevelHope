package co.develhope.forum.cleandatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


// ONLY FOR TEST, CLEAN DATABASE
@Repository
public class CleanDAO {


    @Autowired
    JdbcTemplate jdbcTemplateCleaner;

    public void cleanDB(){
        jdbcTemplateCleaner.update("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplateCleaner.update("TRUNCATE TABLE `user`");
        jdbcTemplateCleaner.update("TRUNCATE TABLE user_data");
        jdbcTemplateCleaner.update("SET FOREIGN_KEY_CHECKS = 1");
    }

}
