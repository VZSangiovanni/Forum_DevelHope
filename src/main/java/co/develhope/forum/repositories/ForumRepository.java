package co.develhope.forum.repositories;

import co.develhope.forum.dao.rowmapper.CategoryRowMapper;
import co.develhope.forum.dao.rowmapper.PostRowMapper;
import co.develhope.forum.dao.rowmapper.TopicRowMapper;
import co.develhope.forum.dto.response.BaseResponse;
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
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createCategory(ForumCategory forumCategory) {
        String SQL = "INSERT INTO forum_category (Category_Title) values (?)";
        int count = 0;
        try {
            count += jdbcTemplate.update(SQL, forumCategory.getCategoryTitle());

            if (count == 1) {
                Integer categoryID = jdbcTemplate.queryForObject
                        ("SELECT id_Forum_Category FROM forum_category WHERE Category_Title = ?",
                                Integer.class, forumCategory.getCategoryTitle());

                forumCategory.setId(categoryID);
            }
            return count == 1;
        } catch (Exception e) {
            log.error("ERROR", e);
            return false;
        }
    }

    public List<Map<String, Object>> readAllCategory() {
        List<Map<String, Object>> forumCategoryList = jdbcTemplate.queryForList("SELECT * FROM forum_category");
        return forumCategoryList;
    }

    //TODO implementare il search
    public ForumCategory searchCategoryByTitle(String categoryTitle) {
        String title = "%" + categoryTitle.trim() + "%";
        ForumCategory forumCategory = jdbcTemplate.queryForObject("SELECT * FROM forum_category WHERE Category_Title LIKE ?",
                new CategoryRowMapper(), title.toLowerCase());
        return forumCategory;
    }

    public ForumCategory findCategoryByTitle(String categoryTitle) {
        ForumCategory forumCategory = jdbcTemplate.queryForObject("SELECT * FROM forum_category WHERE Category_Title = ?",
                new CategoryRowMapper(), categoryTitle);
        return forumCategory;
    }

    public ForumPost findPostByID(int id) {
        try {
            ForumPost forumPost = jdbcTemplate.queryForObject(
                    "SELECT * FROM forum_post AS fp INNER JOIN forum_topic AS ft ON ft.id_Forum_Topic=fp.Forum_Topic_id_Forum_Topic INNER JOIN user AS u ON u.id_User=fp.User_id_User WHERE id_Forum_Post = ?",
                    new PostRowMapper(), id);
            return forumPost;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error("ERROR", e);
            return null;
        }
    }

    public void deleteAllCategory() {
        jdbcTemplate.update("DELETE FROM forum_category");
        jdbcTemplate.update("ALTER TABLE forum_category AUTO_INCREMENT = 1");
        jdbcTemplate.update("ALTER TABLE forum_topic AUTO_INCREMENT = 1");
        jdbcTemplate.update("ALTER TABLE forum_post AUTO_INCREMENT = 1");
    }

    public void deleteCategoryByName(String categoryTitle) {
        jdbcTemplate.update("DELETE FROM forum_category WHERE Category_Title = ?", categoryTitle);
    }

    /**
     * TOPIC REPOSITORY
     */

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
                    forumTopic.getTopicTitle(), forumTopic.getTopicText(),
                    topicCreation, categoryID, userID);
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
        } catch (Exception e) {
            log.error("ERROR", e);
            return false;
        }
    }

    public void updateTopicTitle(String topicTitle, int topicID) {
        String SQLUpdateTitle = "UPDATE forum_topic SET Topic_Title = ? WHERE id_Forum_Topic = ?";
        jdbcTemplate.update(SQLUpdateTitle, topicTitle, topicID);
    }

    public void updateTopicText(String topicText, int topicID) {
        String SQLUpdateText = "UPDATE forum_topic SET Topic_Text = ? WHERE id_Forum_Topic = ?";
        jdbcTemplate.update(SQLUpdateText, topicText, topicID);
    }

    public ForumTopic findTopicByID(int id) {
        try {
            ForumTopic forumTopic = jdbcTemplate.queryForObject("SELECT * FROM forum_topic INNER JOIN forum_category ON id_Forum_Category = Forum_Category_id_Forum_Category INNER JOIN user ON id_User = User_id_User WHERE id_Forum_Topic = ?",
                    new TopicRowMapper(), id);
            return forumTopic;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error("ERROR", e);
            return null;
        }
    }

    public String findTopicTitleByID(int id) {
        try {
            String topicTitle = jdbcTemplate.queryForObject("SELECT Topic_Title FROM forum_topic WHERE id_Forum_Topic = ?",
                    String.class, id);
            return topicTitle;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> readAllTopics() {
        List<Map<String, Object>> forumTopicList = jdbcTemplate.queryForList("SELECT * FROM forum_topic");
        return forumTopicList;
    }

    @Deprecated
    public List<Map<String, Object>> findAllTopic() {
        String SQL = "SELECT * From forum_topic";
        try {
            List<Map<String, Object>> topicList = jdbcTemplate.queryForList(SQL);
            return topicList;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> getMyTopics() {
        String querySQL = "SELECT * FROM forum_topic WHERE User_id_User = ?";
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        int userID = user.getId();
        List<Map<String, Object>> userTopics = jdbcTemplate.queryForList(querySQL, userID);
        return userTopics;
    }

    @Deprecated
    public List<Map<String, Object>> readAllMyTopic() {
        String SQL = "SELECT * FROM forum_topic WHERE User_id_User = ?";
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        int userID = user.getId();
        try {
            List<Map<String, Object>> myTopic = jdbcTemplate.queryForList(SQL, userID);
            return myTopic;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> findByCategory(String categoryTitle) {
        ForumCategory forumCategory = findCategoryByTitle(categoryTitle);
        int categoryID = forumCategory.getId();
        try {
            List<Map<String, Object>> topic = jdbcTemplate.queryForList("SELECT * FROM forum_topic WHERE Forum_Category_id_Forum_Category = ?",
                    categoryID);
            return topic;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Deprecated
    public List<Map<String, Object>> findAllTopicByCategoryTitle(String categoryTitle) {
        String SQL = "SELECT * FROM forum_topic WHERE Forum_Category_id_Forum_Category = ?";
        ForumCategory forumCategory = findCategoryByTitle(categoryTitle);
        int categoryID = forumCategory.getId();
        try {
            List<Map<String, Object>> userTopicList = jdbcTemplate.queryForList(SQL, categoryID);
            return userTopicList;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> findAllTopicByUser(String userName) {
        String SQL = "SELECT * FROM forum_topic WHERE User_id_User = ?";
        User user = userRepository.findByName(userName);
        int userID = user.getId();
        try {
            List<Map<String, Object>> userTopicList = jdbcTemplate.queryForList(SQL, userID);
            return userTopicList;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public void deleteTopicByID(int topicId) {
        jdbcTemplate.update("DELETE FROM forum_topic WHERE id_Forum_Topic = ? ", topicId);
    }

    public void deleteTopicByCategoryIDRepository(int categoryID) {
        jdbcTemplate.update("DELETE FROM forum_topic WHERE Forum_Category_id_Forum_Category = ?", categoryID);
    }

    public void deleteAllTopics() {
        jdbcTemplate.update("DELETE FROM forum_topic");
        jdbcTemplate.update("ALTER TABLE forum_topic AUTO_INCREMENT = 1");
        jdbcTemplate.update("ALTER TABLE forum_post AUTO_INCREMENT = 1");
    }

    /**
     * POST REPOSITORY
     */

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
                    forumPost.getPostText(), postCreation, topicID, userID);
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
        } catch (Exception e) {
            log.error("ERROR", e);
            return false;
        }
    }

    public BaseResponse deleteMyOwnSinglePost(int postID) {
        String querySQL = ("DELETE FROM forum_post WHERE User_id_User = ? AND id_Forum_Post = ?");
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        ForumPost postFromDB = findPostByID(postID);
        int userID = user.getId();
        try {
            if(principal.getUsername().equals(postFromDB.getUserName())){
                jdbcTemplate.update(querySQL, userID, postID);
                return new BaseResponse(BaseResponse.StatusEnum.OK,"Your Post has been successfully deleted");
            } else {
            return new BaseResponse("You have not the permissions required to delete this post");}
        } catch (Exception e) {
            log.error("Error", e);
            return new BaseResponse("An error has occurred");
        }
    }

    public void deletePostByID(int postId) {
        jdbcTemplate.update("DELETE FROM forum_post WHERE id_Forum_Post = ? ", postId);
    }

    public void deleteAllPosts() {
        jdbcTemplate.update("DELETE FROM forum_post");
        jdbcTemplate.update("ALTER TABLE forum_post AUTO_INCREMENT = 1");
    }

    public List<Map<String, Object>> readAllPosts() {
        List<Map<String, Object>> forumPostsList = jdbcTemplate.queryForList("SELECT * FROM forum_post");
        return forumPostsList;
    }

    @Deprecated
    public List<Map<String, Object>> findAllPost() {
        String SQL = "SELECT * From forum_post";
        try {
            List<Map<String, Object>> topicList = jdbcTemplate.queryForList(SQL);
            return topicList;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> readAllMyPost() {
        String SQL = "SELECT * FROM forum_post WHERE User_id_User = ?";
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        int userID = user.getId();
        try {
            List<Map<String, Object>> myTopic = jdbcTemplate.queryForList(SQL, userID);
            return myTopic;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> findAllPostByUser(String userName) {
        String SQL = "SELECT * FROM forum_post WHERE User_id_User = ?";
        User user = userRepository.findByName(userName);
        int userID = user.getId();
        try {
            List<Map<String, Object>> userTopicList = jdbcTemplate.queryForList(SQL, userID);
            return userTopicList;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> findByTopic(int id) {
        try {
            List<Map<String, Object>> post = jdbcTemplate.queryForList("SELECT * FROM forum_post WHERE Forum_Topic_id_Forum_Topic=?", id);
            return post;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Deprecated
    public List<Map<String, Object>> findAllPostByTopicID(int topicID) {
        String SQL = "SELECT * FROM forum_post WHERE Forum_Topic_id_Forum_Topic = ?";
        try {
            List<Map<String, Object>> userTopicList = jdbcTemplate.queryForList(SQL, topicID);
            return userTopicList;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> getMyPosts() {
        String querySQL = "SELECT * FROM forum_post WHERE User_id_User =?";
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        User user = userRepository.findByName(principal.getUsername());
        int userID = user.getId();
        List<Map<String, Object>> userPosts = jdbcTemplate.queryForList(querySQL, userID);
        return userPosts;
    }

    public void changeTopicCategory(int categoryID, int topicID) {
        String SQLChangeCategory = "UPDATE forum_topic SET Forum_Category_id_Forum_Category = ? WHERE id_Forum_Topic = ?";
        jdbcTemplate.update(SQLChangeCategory, categoryID, topicID);
    }

    public void updatePostText(String postText, int postID) {
        String SQLUpdateText = "UPDATE forum_post SET Post_Text = ? WHERE id_Forum_Post = ?";
        jdbcTemplate.update(SQLUpdateText, postText, postID);
    }

    public void changePostTopic(int topicID, int postID) {
        String SQLChangeCategory = "UPDATE forum_post SET Forum_Topic_id_Forum_Topic = ? WHERE id_Forum_Post = ?";
        jdbcTemplate.update(SQLChangeCategory, topicID, postID);
    }


}