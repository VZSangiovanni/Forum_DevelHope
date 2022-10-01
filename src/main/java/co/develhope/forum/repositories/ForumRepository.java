package co.develhope.forum.repositories;

import co.develhope.forum.dao.rowmapper.CategoryRowMapper;
import co.develhope.forum.dao.rowmapper.PostRowMapper;
import co.develhope.forum.dao.rowmapper.TopicRowMapper;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.model.User;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ForumRepository {

    private static final Logger log = LoggerFactory.getLogger(ForumRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

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

    // Under this comment place the Topic Repository

    public boolean createTopic(ForumTopic forumTopic, String categoryTitle) {
        String SQLTopic = "INSERT INTO forum_topic (Topic_Title, Topic_Text, Topic_Creation, Forum_Category_id_Forum_Category, User_id_User) VALUES (?, ?, ?, ?, ?)";
        ForumCategory forumCategory = findCategoryByTitle(categoryTitle);
        int categoryID = forumCategory.getId();
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        int userID = user.getId();
        //creiamo il Topic_Creation prima delle query per fissarlo nel sistema
        Long topicCreation = System.currentTimeMillis();
        int count = 0;
        try {
            count += jdbcTemplate.update(SQLTopic,
                    new Object[]{forumTopic.getTopicTitle(), forumTopic.getTopicText(),
                            topicCreation, categoryID, userID});
            if (count == 1) {
                String topicCategory = forumCategory.getCategoryTitle();
                forumTopic.setTopicCategory(topicCategory);
                String userName = user.getUsername();
                forumTopic.setUserName(userName);
                Integer topicID = jdbcTemplate.queryForObject(
                        "SELECT id_Forum_Topic FROM forum_topic WHERE Topic_Creation = ?",
                        Integer.class, topicCreation);
                forumTopic.setId(topicID);
            }
            return count == 1;
        }catch (Exception e) {
            log.error("ERROR", e);
            return false;
        }
    }

    public ForumTopic findTopicByID(int id) {
        try {
            ForumTopic forumTopic = jdbcTemplate.queryForObject("SELECT * FROM forum_topic,forum_category,user WHERE id_Forum_Topic = ?",
                    new TopicRowMapper(), id);
            return forumTopic;
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    public String findTopicTitleByID(int id) {
        try {
            String topicTitle = jdbcTemplate.queryForObject("SELECT Topic_Title FROM forum_topic WHERE id_Forum_Topic = ?",
                    String.class, id);
            return topicTitle;
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    // Under this comment place the Post Repository

    public boolean createPost(ForumPost forumPost, int topicID) {
        String SQLPost = "INSERT INTO forum_post (Post_Text, Post_Creation, Forum_Topic_id_Forum_Topic, User_id_User) VALUES (?, ?, ?, ?)";
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        int userID = user.getId();
        //creiamo il Post_Creation prima delle query per fissarlo nel sistema
        Long postCreation = System.currentTimeMillis();
        int count = 0;
        try {
            count += jdbcTemplate.update(SQLPost,
                    new Object[]{forumPost.getPostText(), postCreation, topicID, userID});
            if (count == 1) {
                String topicTitle = findTopicTitleByID(topicID);
                forumPost.setPostTopic(topicTitle);
                String userName = user.getUsername();
                forumPost.setUserName(userName);
                Integer postID = jdbcTemplate.queryForObject(
                        "SELECT id_Forum_Post FROM forum_post WHERE Post_Creation = ?",
                        Integer.class, postCreation);
                forumPost.setId(postID);
            }
            return count == 1;
        }catch (Exception e) {
            log.error("ERROR", e);
            return false;
        }
    }
    public List<Map<String, Object>>readAllTopics() {
        List<Map<String, Object>> forumTopicList = jdbcTemplate.queryForList("SELECT * FROM forum_topic");
        return forumTopicList;
    }
    public List<Map<String, Object>> getMyTopics() {
        String querySQL = "SELECT * FROM forum_topic where User_id_User =?";
        List<Map<String, Object>> userTopics = jdbcTemplate.queryForList(querySQL, new TopicRowMapper());
        return userTopics;

    }

    public ForumTopic findByCategory() {
        try {
            ForumTopic topic = jdbcTemplate.queryForObject("SELECT * FROM forum_category fc INNER JOIN forum_topic ft ON t.id_Forum_Category=fc.Forum_Category_id_Forum_Category WHERE t.forum_category_id_Forum_Category=?",
                    new TopicRowMapper());
            int topicModelID = jdbcTemplate.queryForObject("SELECT id_Forum_Category FROM `forum_category` WHERE id_Forum_Category =?",
                    Integer.class, new Object[]{topic});
            topic.setTopicCategory(topic.getTopicCategory());
            return topic;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    public List<Map<String, Object>>readAllPosts() {
        List<Map<String, Object>> forumPostsList = jdbcTemplate.queryForList("SELECT * FROM forum_post");
        return forumPostsList;
    }
    public List<Map<String, Object>> getMyPosts() {
        String querySQL = "SELECT * FROM forum_post where User_id_User =?";
        List<Map<String, Object>> userTopics = jdbcTemplate.queryForList(querySQL, new TopicRowMapper());
        return userTopics;

    }

    public ForumPost findByTopic() {
        try {
            ForumPost post = jdbcTemplate.queryForObject("SELECT * FROM forum_topic ft INNER JOIN forum_post fp ON p.id_Forum_Topic=ft.Forum_Topic_id_Forum_Topic WHERE p.forum_Topic_id_Forum_Topic=?",
                    new PostRowMapper());
            int postModelID = jdbcTemplate.queryForObject("SELECT id_Forum_Topic FROM `forum_Topic` WHERE id_Forum_Topic =?",
                    Integer.class, new Object[]{post});
            post.setPostTopic(post.getPostTopic());
            return post;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}