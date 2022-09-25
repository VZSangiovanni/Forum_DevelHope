package co.develhope.forum.repositories;

import co.develhope.forum.dao.rowmapper.CategoryRowMapper;
import co.develhope.forum.model.ForumCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> readAllCategory() {
        List<Map<String, Object>> forumCategoryList = jdbcTemplate.queryForList("SELECT * FROM forum_category");
        return forumCategoryList;
    }


    public ForumCategory findCategoryByTitle(String categoryTitle){
        ForumCategory forumCategory = jdbcTemplate.queryForObject("SELECT * FROM forum_category WHERE Category_Title = ?",
                new CategoryRowMapper(), categoryTitle.toLowerCase().trim());
        return forumCategory;
    }


    public void deleteAllCategory() {
        jdbcTemplate.update("DELETE FROM forum_category");
        jdbcTemplate.update("ALTER TABLE forum_category AUTO_INCREMENT = 1");
    }

    public void deleteCategoryByName(String categoryTitle) {
        jdbcTemplate.update("DELETE FROM forum_category WHERE Category_Title = ?", categoryTitle);
    }



}
