package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.PostDTO;
import co.develhope.forum.dto.response.TopicDTO;
import co.develhope.forum.dto.response.UpdatePostDTO;
import co.develhope.forum.dto.response.UpdateTopicDTO;
import co.develhope.forum.exception.ForumCategoryTitleAlreadyExistException;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.repositories.ForumRepository;

import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ForumService {


    @Autowired
    private ForumRepository forumRepository;

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
            System.out.println(forumCategory);
            return forumCategory;
        }
    }

    public List<Map<String, Object>> readAllCategory() {
        return forumRepository.readAllCategory();
    }

    public ForumCategory findCategoryByTitle(String categoryTitle) {
        return forumRepository.findCategoryByTitle(categoryTitle);
    }

    public void deleteAllPosts() {
        forumRepository.deleteAllPosts();
    }
    //TODO INTEGRATE

    public void deleteAllCategory() {
        forumRepository.deleteAllCategory();
    }

    public void deleteOneCategory(String categoryTitle) {
        forumRepository.deleteCategoryByName(categoryTitle);
    }
    //TODO INTEGRATE

    // Under this comment place the Topic Services
    public BaseResponse createTopic(ForumTopic forumTopic, String categoryTitle) {

        forumRepository.createTopic(forumTopic, categoryTitle);

        System.out.println(forumTopic.toString());

        return forumTopic;
    }

    public void deleteTopic(int topicID) {
        forumRepository.deleteTopicByID(topicID);
    }
    //TODO INTEGRATE

    public void deleteAllTopics() {
        forumRepository.deleteAllTopics();
    }
    //TODO INTEGRATE

    public List<Map<String, Object>> findAllTopic() {
        return forumRepository.findAllTopic();
    }

    public List<Map<String, Object>> findAllTopicByUser(String userName) {
        return forumRepository.findAllTopicByUser(userName);
    }

    public List<Map<String, Object>> readAllMyTopic() {
        return forumRepository.readAllMyTopic();
    }

    public List<Map<String, Object>> findAllTopicByCategoryTitle(String categoryTitle) {
        return forumRepository.findAllTopicByCategoryTitle(categoryTitle);
    }

    public BaseResponse updateTopicTitle(UpdateTopicDTO updateTopicDTO, int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN")
                || principal.getRoles().contains("ROLE_FOUNDER")
                || principal.getUsername().equals(forumTopic.getUserName())) {
            forumTopic.setTopicTitle(updateTopicDTO.getTopicTitle());
            forumRepository.updateTopicTitle(forumTopic.getTopicTitle(), forumTopic.getId());
            return new UpdateTopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(),
                    forumTopic.getTopicText(), forumTopic.getTopicCategory());
        } else {
            return new BaseResponse("Not your Topic");
        }
    }

    public BaseResponse updateTopicText(UpdateTopicDTO updateTopicDTO, int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN")
                || principal.getRoles().contains("ROLE_FOUNDER")
                || principal.getUsername().equals(forumTopic.getUserName())) {
            forumTopic.setTopicText(updateTopicDTO.getTopicText());
            forumRepository.updateTopicText(forumTopic.getTopicText(), forumTopic.getId());
            return new UpdateTopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(),
                    forumTopic.getTopicText(), forumTopic.getTopicCategory());
        } else {
            return new BaseResponse("Not your Topic");
        }
    }

    public BaseResponse changeTopicCategory(UpdateTopicDTO updateTopicDTO, int topicID) {
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        ForumCategory forumCategory = forumRepository.findCategoryByTitle(updateTopicDTO.getTopicCategory());
        int categoryID = forumCategory.getId();
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (forumCategory == null) return new BaseResponse("Category not Found");
        forumTopic.setTopicCategory(updateTopicDTO.getTopicCategory());
        forumRepository.changeTopicCategory(categoryID, topicID);
        return new UpdateTopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(), forumTopic.getTopicText(),
                forumTopic.getTopicCategory());
    }

    public BaseResponse deleteTopicByID(int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN")
                || principal.getRoles().contains("ROLE_FOUNDER")
                || principal.getUsername().equals(forumTopic.getUserName())) {
            forumRepository.deleteTopicByID(topicID);
            return new BaseResponse(BaseResponse.StatusEnum.OK, "Topic Deleted");
        } else {
            return new BaseResponse("You cannot delete this Topic");
        }
    }

    // Under this comment place the Post Services

    public BaseResponse createPost(ForumPost forumPost, int topicID) {
        forumRepository.createPost(forumPost, topicID);
        System.out.println(forumPost.toString());
        return forumPost;
    }

    public void deletePost(int postID) {
        forumRepository.deletePostByID(postID);
    }

    public List<Map<String, Object>> findAllPost() {
        return forumRepository.findAllPost();
    }

    public List<Map<String, Object>> readAllMyPost() {
        return forumRepository.readAllMyPost();
    }

    public List<Map<String, Object>> findAllPostByUser(String userName) {
        return forumRepository.findAllPostByUser(userName);
    }

    public List<Map<String, Object>> findAllPostByTopicID(int topicID) {
        return forumRepository.findAllPostByTopicID(topicID);
    }

    public BaseResponse updatePostText(UpdatePostDTO updatePostDTO, int postID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumPost forumPost = forumRepository.findPostByID(postID);
        if (forumPost == null) return new BaseResponse("Post not Found");
        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN")
                || principal.getRoles().contains("ROLE_FOUNDER")
                || principal.getUsername().equals(forumPost.getUserName())) {
            forumPost.setPostText(updatePostDTO.getPostText());
            forumRepository.updatePostText(forumPost.getPostText(), forumPost.getId());
            return new UpdatePostDTO(forumPost.getId(), forumPost.getPostText(), forumPost.getPostTopic());
        } else {
            return new BaseResponse("Not your Post");
        }
    }



    public BaseResponse deletePostByID(int postID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumPost forumPost = forumRepository.findPostByID(postID);
        if (forumPost == null) return new BaseResponse("Post not Found");
        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN")
                || principal.getRoles().contains("ROLE_FOUNDER")
                || principal.getUsername().equals(forumPost.getUserName())) {
            forumRepository.deletePostByID(postID);
            return new BaseResponse(BaseResponse.StatusEnum.OK, "Post Deleted");
        } else {
            return new BaseResponse("You cannot delete this Post");
        }
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
           forumRepository.updateTopicTitle(forumTopic.getTopicTitle(),forumTopic.getId());
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
            forumRepository.updateTopicText(forumTopic.getTopicText(),forumTopic.getId());
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
            forumPost.setPostText(postDTO.getPostText());
            forumRepository.updatePostText(forumPost.getPostText(), forumPost.getId());
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


