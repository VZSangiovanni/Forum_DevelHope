package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.UpdateTopicDTO;
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
        if (forumRepository.checkCategoryTitleExist(categoryTitle)){
            throw new ForumCategoryTitleAlreadyExistException(categoryTitle);
        }else {
            return new BaseResponse(BaseResponse.StatusEnum.OK, "Category Title Available");
        }
    }

    public BaseResponse createCategory(ForumCategory forumCategory) {
        if (forumRepository.checkCategoryTitleExist(forumCategory.getCategoryTitle())){
            throw new ForumCategoryTitleAlreadyExistException(forumCategory.getCategoryTitle());
        }else {
            forumRepository.createCategory(forumCategory);
            System.out.println(forumCategory.toString());
            return forumCategory;
        }
    }

    public List<Map<String, Object>> readAllCategory() {
        return forumRepository.readAllCategory();
    }


    public ForumCategory findCategoryByTitle(String categoryTitle){
        return forumRepository.findCategoryByTitle(categoryTitle);
    }


    public void deleteAllCategory(){
        forumRepository.deleteAllCategory();
    }

    public void deleteOneCategory(String categoryTitle){
        forumRepository.deleteCategoryByName(categoryTitle);
    }

    // Under this comment place the Topic Services
    public BaseResponse createTopic(ForumTopic forumTopic, String categoryTitle) {

        forumRepository.createTopic(forumTopic, categoryTitle);

        System.out.println(forumTopic.toString());

        return forumTopic;
    }

    public List<Map<String, Object>> findAllTopic() {
        return forumRepository.findAllTopic();
    }

    public List<Map<String, Object>> findAllTopicByUser(String userName) {
       return forumRepository.findAllTopicByUser(userName);
    }

    public List<Map<String, Object>> readAllMyTopic(){
        return forumRepository.readAllMyTopic();
    }

    public List<Map<String, Object>> findAllTopicByCategoryTitle(String categoryTitle) {
        return forumRepository.findAllTopicByCategoryTitle(categoryTitle);
    }

    public BaseResponse updateTopicTitle(UpdateTopicDTO updateTopicDTO, int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (principal.getRoles().contains("ROLE_MOD") ||principal.getRoles().contains("ROLE_ADMIN")||principal.getRoles().contains("ROLE_FOUNDER")||principal.getUsername().equals(forumTopic.getUserName())) {
            forumTopic.setTopicTitle(updateTopicDTO.getTopicTitle());
            forumRepository.updateTopicTitle(forumTopic.getTopicTitle(), forumTopic.getId());
            return new UpdateTopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(), forumTopic.getTopicText(), forumTopic.getTopicCategory());
        }else {
            return new BaseResponse("Not your Topic");
        }
    }

    public BaseResponse updateTopicText(UpdateTopicDTO updateTopicDTO, int topicID) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (principal.getRoles().contains("ROLE_MOD") ||principal.getRoles().contains("ROLE_ADMIN")||principal.getRoles().contains("ROLE_FOUNDER")||principal.getUsername().equals(forumTopic.getUserName())) {
            forumTopic.setTopicText(updateTopicDTO.getTopicText());
            forumRepository.updateTopicText(forumTopic.getTopicText(), forumTopic.getId());
            return new UpdateTopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(), forumTopic.getTopicText(), forumTopic.getTopicCategory());
        }else {
            return new BaseResponse("Not your Topic");
        }
    }

    public BaseResponse changeTopicCategory(UpdateTopicDTO updateTopicDTO, int topicID){
        ForumTopic forumTopic = forumRepository.findTopicByID(topicID);
        ForumCategory forumCategory = forumRepository.findCategoryByTitle(updateTopicDTO.getTopicCategory());
        int categoryID = forumCategory.getId();
        if (forumTopic == null) return new BaseResponse("Topic not Found");
        if (forumCategory == null) return new BaseResponse("Category not Found");
        forumTopic.setTopicCategory(updateTopicDTO.getTopicCategory());
        forumRepository.changeTopicCategory(categoryID, topicID);
        return new UpdateTopicDTO(forumTopic.getId(), forumTopic.getTopicTitle(), forumTopic.getTopicText(), forumTopic.getTopicCategory());
    }

    // Under this comment place the Post Services

    public BaseResponse createPost(ForumPost forumPost, int topicID) {
        forumRepository.createPost(forumPost, topicID);
        System.out.println(forumPost.toString());
        return forumPost;
    }

    public List<Map<String, Object>> findAllPost() {
        return forumRepository.findAllPost();
    }

    public List<Map<String, Object>> readAllMyPost(){
        return forumRepository.readAllMyPost();
    }

    public List<Map<String, Object>> findAllPostByUser(String userName){
        return forumRepository.findAllPostByUser(userName);
    }

    public List<Map<String, Object>> findAllPosyByTopicID(int topicID) {
        return forumRepository.findAllPostByTopicID(topicID);
    }



}
