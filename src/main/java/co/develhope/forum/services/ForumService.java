package co.develhope.forum.services;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.exception.ForumCategoryTitleAlreadyExistException;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.repositories.ForumRepository;
import co.develhope.forum.repositories.UserRepository;
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

    //NEED FIX
    public ForumCategory findCategoryByTitle(String categoryTitle){
        return forumRepository.findCategoryByTitle(categoryTitle);
    }

    public void deleteAllCategory(){
        forumRepository.deleteAllCategory();
    }

    public void deleteOneCategory(String categoryTitle){
        forumRepository.deleteCategoryByName(categoryTitle);
    }


}
