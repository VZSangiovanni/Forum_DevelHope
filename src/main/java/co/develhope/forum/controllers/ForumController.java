package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.services.ForumService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @PostMapping("/category/create")
    public BaseResponse createCategory(@RequestBody ForumCategory forumCategory) {
        return forumService.createCategory(forumCategory);
    }

    @ZeroSecurity
    @GetMapping("/category/read-all")
    public List<Map<String, Object>> readAllCategory() {
        return forumService.readAllCategory();
    }


    @ZeroSecurity
    @GetMapping("/category/read/{categoryTitle}")
    public ForumCategory findCategoryByTitle(@PathVariable String categoryTitle){
        return forumService.findCategoryByTitle(categoryTitle);
    }


    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/category/delete/all")
    public void deleteAllCategory() {
        forumService.deleteAllCategory();
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/category/delete/{categoryTitle}")
    public void deleteOneCategory(@PathVariable String categoryTitle){
        forumService.deleteOneCategory(categoryTitle);
    }

}
