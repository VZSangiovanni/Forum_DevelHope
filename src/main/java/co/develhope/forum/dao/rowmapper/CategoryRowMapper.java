package co.develhope.forum.dao.rowmapper;

import co.develhope.forum.model.ForumCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<ForumCategory> {

    @Override
    public ForumCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForumCategory forumCategory = new ForumCategory();
        forumCategory.setId(rs.getInt("id_Forum_Category"));
        forumCategory.setCategoryTitle(rs.getString("Category_Title"));
        return forumCategory;
    }
}
