package co.develhope.forum.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

 //All'interno dei DAO vanno i metodi che fanno le query

@Repository
public class UserDAO {

@Autowired
    JdbcTemplate jdbcTemplate;
    //questo serve a fare le query
}
