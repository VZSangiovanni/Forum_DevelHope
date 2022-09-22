package co.develhope.forum.repositories;

import co.develhope.forum.model.ForumCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ForumRepository {

    private static final Logger log = LoggerFactory.getLogger(ForumRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkCategoryTitleExist(String categoryTitle) {
        try {
            return jdbcTemplate.queryForObject("SELECT 1 FROM forum_category WHERE Category_Title = ?",
                    Integer.class, categoryTitle) != null;
        }catch (Exception e){
            return false;
        }
    }


    public boolean createCategory(ForumCategory forumCategory){
            String SQL = "INSERT INTO forum_category (Category_Title) values (?)";
            int count = 0;
        try{
            count+= jdbcTemplate.update(SQL, new Object[]{forumCategory.getCategoryTitle()});

           if (count == 1) {
               Integer categoryID = jdbcTemplate.queryForObject
                       ("SELECT id_Forum_Category FROM forum_category WHERE Category_Title = ?",
                               Integer.class, new Object[]{forumCategory.getCategoryTitle()});

               forumCategory.setId(categoryID);
           }

            return count == 1;
        }catch (Exception e){
            log.error("ERROR", e);
            return false;
        }
    }

    public void deleteAllCategory() {
        //"TRUNCATE TABLE forum_category"
        jdbcTemplate.update("DELETE FROM forum_category");
        jdbcTemplate.update("ALTER TABLE forum_category AUTO_INCREMENT = 1");
    }



}
