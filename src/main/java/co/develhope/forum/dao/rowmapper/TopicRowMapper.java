package co.develhope.forum.dao.rowmapper;

import co.develhope.forum.model.ForumTopic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicRowMapper implements RowMapper<ForumTopic> {


    @Override
    public ForumTopic mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForumTopic forumTopic = new ForumTopic();
        forumTopic.setId(rs.getInt("id_Forum_Topic"));
        forumTopic.setTopicTitle(rs.getString("Topic_Title"));
        forumTopic.setTopicText(rs.getString("Topic_Text"));
        forumTopic.setTopicCreation(rs.getLong("Topic_Creation"));
        forumTopic.setTopicCategory(rs.getString("Category_Title"));
        forumTopic.setUserName(rs.getString("User_Name"));
        return forumTopic;
    }
}
