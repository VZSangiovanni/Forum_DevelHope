package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.exception.ForumCategoryTitleAlreadyExistException;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

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
    //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
    public void deleteAllPost(){
        forumRepository.deleteAllPost();
    }
    //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
    public void deleteAllTopics() {
        forumRepository.deleteAllTopics();
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
    //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
    public void deleteTopic(int topicID) {
        forumRepository.deleteTopic(topicID);
    }

    // Under this comment place the Post Services

    public BaseResponse createPost(ForumPost forumPost, int topicID) {
        forumRepository.createPost(forumPost, topicID);
        System.out.println(forumPost.toString());
        return forumPost;
    }
    //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
    public void deletePost(int postID) {
        forumRepository.deletePost(postID);
    }
    //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
    public void deleteMyPost(int postID) {
        forumRepository.deleteThisPost(postID);
    }
}
