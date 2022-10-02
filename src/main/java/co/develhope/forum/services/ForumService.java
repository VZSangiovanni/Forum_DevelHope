package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.PostDTO;
import co.develhope.forum.dto.response.TopicDTO;
import co.develhope.forum.exception.ForumCategoryTitleAlreadyExistException;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.model.User;
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
        return  forumRepository.findByCategory(categoryTitle);
    }

    public BaseResponse userUpdateTopicTitle(TopicDTO topicDTO) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();


        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN") || principal.getRoles().contains("ROLE_FOUNDER") || principal.getUsername().equals("username_5")) {

            String topicTitle = topicDTO.getTopicTitle();
            forumRepository.userUpdateTopicTitle(topicTitle);


            return new BaseResponse(BaseResponse.StatusEnum.OK, "updated");

        } else {
            return new BaseResponse("not updated");
        }
    }


    public BaseResponse userUpdateTopicText(TopicDTO topicDTO) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();

        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN") || principal.getRoles().contains("ROLE_FOUNDER") || principal.getUsername().equals("username_5")) {
            String topicText = topicDTO.getTopicText();

            forumRepository.userUpdateTopicText(topicText);

            return new BaseResponse(BaseResponse.StatusEnum.OK, "updated");

        } else {
            return new BaseResponse(BaseResponse.StatusEnum.OK, "not updated");
        }
    }


    public BaseResponse postUpdateText(PostDTO postDTO) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();

        if (principal.getRoles().contains("ROLE_MOD") || principal.getRoles().contains("ROLE_ADMIN") || principal.getRoles().contains("ROLE_FOUNDER") || principal.getUsername().equals("username_5")) {

            String postText = postDTO.getPostText();

            forumRepository.postUpdateText(postText);

            return new BaseResponse(BaseResponse.StatusEnum.OK, "text updated");

        } else {

            return new BaseResponse(BaseResponse.StatusEnum.OK, " text not updated");
        }


    }
}
    public List<Map<String, Object>> findAllPosts() {
        return forumRepository.readAllPosts();
    }

    public List<Map<String, Object>> findMyPosts() {
        return forumRepository.getMyPosts();
    }
    public List<Map<String, Object>> findAllPostsByTopic(int id){
        return forumRepository.findByTopic(id);
    }    }


