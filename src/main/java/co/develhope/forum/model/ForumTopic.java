package co.develhope.forum.model;

import co.develhope.forum.dto.response.BaseResponse;

public class ForumTopic extends BaseResponse {

    private int id;
    private String topicTitle;
    private String topicText;
    private long topicCreation;
    private String topicCategory;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public long getTopicCreation() {
        return topicCreation;
    }

    public void setTopicCreation(long topicCreation) {
        this.topicCreation = topicCreation;
    }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
