package co.develhope.forum.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//All'interno dei DAO vanno i metodi che fanno le query

@Repository
public class UserDAO {

    //questo serve a fare le query
    @Autowired
    JdbcTemplate jdbcTemplate;


    //public UserDAO(JdbcTemplate jdbcTemplate){
    //    this.jdbcTemplate = jdbcTemplate;
    //}

    public boolean checkUserEmailExist(String userEmail){
      try {
          return jdbcTemplate.queryForObject("SELECT 1 FROM user_data WHERE User_Email = ?", Integer.class, userEmail) != null;
      }catch (Exception e){
         return false;
      }

    }


}
