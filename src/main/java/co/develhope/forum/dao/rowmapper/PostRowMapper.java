package co.develhope.forum.dao.rowmapper;

import co.develhope.forum.model.ForumPost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<ForumPost> {

    @Override
    public ForumPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForumPost forumPost = new ForumPost();
        forumPost.setId(rs.getInt("id_Forum_Post"));
        forumPost.setPostText(rs.getString("Post_Text"));
        forumPost.setPostCreation(rs.getLong("Post_Creation"));
        forumPost.setPostTopic(rs.getString("Topic_Title"));
        forumPost.setUserName(rs.getString("User_Name"));
        return forumPost;
    }
}
