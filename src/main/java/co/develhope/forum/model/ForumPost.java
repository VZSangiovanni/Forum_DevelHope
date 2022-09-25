package co.develhope.forum.model;

import co.develhope.forum.dto.response.BaseResponse;

public class ForumPost extends BaseResponse {

    private int id;
    private String postText;
    private long postCreation;
    private String postTopic;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public long getPostCreation() {
        return postCreation;
    }

    public void setPostCreation(long postCreation) {
        this.postCreation = postCreation;
    }

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
