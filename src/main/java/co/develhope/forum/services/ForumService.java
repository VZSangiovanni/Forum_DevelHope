package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.PostDTO;
import co.develhope.forum.dto.response.TopicDTO;
import co.develhope.forum.exception.ForumCategoryTitleAlreadyExistException;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.repositories.ForumRepository;
import co.develhope.forum.repositories.UserRepository;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ForumService {


    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;

    public BaseResponse checkedCategoryTitle(String categoryTitle) {
        if (forumRepository.checkCategoryTitleExist(categoryTitle)) {
            throw new ForumCategoryTitleAlreadyExistException(categoryTitle);
        } else {
            return new BaseResponse(BaseResponse.StatusEnum.OK, "Category Title Available");
        }
    }

    public BaseResponse createCategory(ForumCategory forumCategory) {
        if (forumRepository.checkCategoryTitleExist(forumCategory.getCategoryTitle())) {
            throw new ForumCategoryTitleAlreadyExistException(forumCategory.getCategoryTitle());
        } else {
            forumRepository.createCategory(forumCategory);
            System.out.println(forumCategory.toString());
            return forumCategory;
        }
    }

    public List<Map<String, Object>> readAllCategory() {
        return forumRepository.readAllCategory();
    }


    public ForumCategory findCategoryByTitle(String categoryTitle) {
        return forumRepository.findCategoryByTitle(categoryTitle);
    }


    public void deleteAllCategory() {
        forumRepository.deleteAllCategory();
    }

    public void deleteOneCategory(String categoryTitle) {
        forumRepository.deleteCategoryByName(categoryTitle);
    }

    // Under this comment place the Topic Services
    public BaseResponse createTopic(ForumTopic forumTopic, String categoryTitle) {

        forumRepository.createTopic(forumTopic, categoryTitle);

        System.out.println(forumTopic.toString());

        return forumTopic;
    }

    // Under this comment place the Post Services

    public BaseResponse createPost(ForumPost forumPost, int topicID) {
        forumRepository.createPost(forumPost, topicID);
        System.out.println(forumPost.toString());
        return forumPost;
    }

    public List<Map<String, Object>> findAllTopics() {
        return forumRepository.readAllTopics();
    }

    public List<Map<String, Object>> findMyTopics() {
        return forumRepository.getMyTopics();
    }


    public List<Map<String, Object>> findAllTopicsByCategory(String categoryTitle) {
        return forumRepository.findByCategory(categoryTitle);
    }

    public BaseResponse userUpdateTopicTitle(TopicDTO topicDTO,int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if(forumTopic == null) return new BaseResponse("Topic not found");
        if (principal.getRoles().contains("ROLE_MOD") ||
                principal.getRoles().contains("ROLE_ADMIN") ||
                principal.getRoles().contains("ROLE_FOUNDER") ||
                principal.getUsername().equals(forumTopic.getUserName())) {

           forumTopic.setTopicTitle(topicDTO.getTopicTitle());
           forumRepository.userUpdateTopicTitle(forumTopic.getTopicTitle(),forumTopic.getId());
           return new TopicDTO(forumTopic.getId(),forumTopic.getTopicTitle(),forumTopic.getTopicText(),
                   forumTopic.getTopicCategory());

        } else {
            return new BaseResponse("not your topic");
        }
    }


    public BaseResponse userUpdateTopicText(TopicDTO topicDTO,int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if(forumTopic == null) return new BaseResponse("Topic not Found");
        if (principal.getRoles().contains("ROLE_MOD") ||
                principal.getRoles().contains("ROLE_ADMIN") ||
                principal.getRoles().contains("ROLE_FOUNDER") ||
                principal.getUsername().equals(forumTopic.getUserName())) {

            forumTopic.setTopicText(topicDTO.getTopicText());
            forumRepository.userUpdateTopicText(forumTopic.getTopicText(),forumTopic.getId());
            return new TopicDTO(forumTopic.getId(),forumTopic.getTopicTitle(),forumTopic.getTopicText(),
                    forumTopic.getTopicCategory());

        } else {
            return new BaseResponse("Not your Topic");
        }
    }


    public BaseResponse postUpdateText(PostDTO postDTO,int postID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumPost forumPost = forumRepository.findPostByID(postID);
        if (forumPost == null) return new BaseResponse("Post not Found");
        if (principal.getRoles().contains("ROLE_MOD") ||principal.getRoles().contains("ROLE_ADMIN")
                ||principal.getRoles().contains("ROLE_FOUNDER")
                ||principal.getUsername().equals(forumPost.getUserName())){
            forumPost.setPostText(PostDTO.getPostText());
            forumRepository.postUpdateText(forumPost.getPostText(), forumPost.getId());
            return new PostDTO(forumPost.getId(),forumPost.getPostText(), forumPost.getPostTopic());
        }else {
            return new BaseResponse("Not your Post");
        }
    }

    public BaseResponse changePostTopic(int topicID, int postID) {
        ForumPost forumPost = forumRepository.findPostByID(postID);
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (forumPost == null) return new BaseResponse("Post not Found");
        forumRepository.findTopicTitleByID(topicID);
        String topicTitle = forumRepository.findTopicTitleByID(topicID);
        forumPost.setPostTopic(topicTitle);
        forumRepository.changePostTopic(topicID, postID);
        return new PostDTO(forumPost.getId(), forumPost.getPostText(), forumPost.getPostTopic());
    }

    public BaseResponse changeTopicCategory(TopicDTO updateTopicDTO, int topicID){
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        ForumCategory forumCategory = forumRepository.findCategoryByTitle(updateTopicDTO.getTopicCategory());
        int categoryID = forumCategory.getId();
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (forumCategory == null) return new BaseResponse("Category not Found");
        forumTopic.setTopicCategory(updateTopicDTO.getTopicCategory());
        forumRepository.changeTopicCategory(categoryID, topicID);
        return new TopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(), forumTopic.getTopicText(),
                forumTopic.getTopicCategory());
    }




    public List<Map<String, Object>> findAllPosts() {
        return forumRepository.readAllPosts();
    }

    public List<Map<String, Object>> findMyPosts() {
        return forumRepository.getMyPosts();
    }

    public List<Map<String, Object>> findAllPostsByTopic(int id) {
        return forumRepository.findByTopic(id);
    }
}


