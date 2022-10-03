package co.develhope.forum.dto.response;
public class UpdateTopicDTO extends BaseResponse {

    private int id;

    private String topicTitle;

    private String topicText;

    private String topicCategory;

    public UpdateTopicDTO() {}

    public UpdateTopicDTO(int id, String topicTitle, String topicText, String topicCategory) {
        this.id = id;
        this.topicTitle = topicTitle;
        this.topicText = topicText;
        this.topicCategory = topicCategory;
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
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }
}