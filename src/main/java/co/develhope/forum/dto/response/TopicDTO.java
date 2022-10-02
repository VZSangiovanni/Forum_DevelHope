package co.develhope.forum.dto.response;

public class TopicDTO extends BaseResponse{


    private int id;
    String topicTitle;
    String topicText;
    String TopicCategory;



    public TopicDTO(){}



    public TopicDTO(int id, String topicTitle, String topicText, String topicCategory) {
        this.id = id;
        this.topicTitle = topicTitle;
        this.topicText = topicText;
        TopicCategory = topicCategory;
    }

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

    public String getTopicCategory() {
        return TopicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        TopicCategory = topicCategory;
    }
}
