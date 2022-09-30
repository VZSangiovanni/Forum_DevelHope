package co.develhope.forum.dao.rowmapper;

import co.develhope.forum.model.Topic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicRow implements RowMapper<Topic> {


    @Override
    public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Topic topic = new Topic();
        topic.setIdForumTopic(rs.getInt(1));
        topic.setCreation(rs.getFloat(4));
        topic.setText(rs.getNString(3));
        topic.setTitle(rs.getString(2));
        topic.setIdUser(rs.getInt(6));
        topic.setIdCategory(rs.getInt(5));
        return null;
    }
}
