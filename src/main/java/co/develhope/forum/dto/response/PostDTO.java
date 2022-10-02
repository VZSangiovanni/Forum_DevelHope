package co.develhope.forum.dto.response;

public class PostDTO  extends BaseResponse {

    private int id;
    private String postText;
    private String postTopic;

    public PostDTO(){};



    public PostDTO(int id, String postText, String postTopic) {
        this.id = id;
        this.postText = postText;
        this.postTopic = postTopic;
    }

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

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }
}


