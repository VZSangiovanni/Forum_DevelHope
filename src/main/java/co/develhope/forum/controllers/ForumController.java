package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
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

    // Under this comment place the Topic Controller
    @ZeroSecurity
    @PostMapping("/topic/create/{categoryTitle}")
    public BaseResponse createTopic(@RequestBody ForumTopic forumTopic, @PathVariable String categoryTitle) {
        return forumService.createTopic(forumTopic, categoryTitle);
    }

    @RoleSecurity(value = {"ROLE_MOD", "ROLE_ADMIN", "ROLE_FOUNDER"})
    @GetMapping("/topic/read-all-by-user/{userName}")
    public List<Map<String, Object>> readAllTopicByUser(@PathVariable String userName) {
        return forumService.findAllTopicByUser(userName);
    }

    @RoleSecurity(value = {"ROLE_MOD", "ROLE_ADMIN", "ROLE_FOUNDER"})
    @GetMapping("/topic/read-all")
    public List<Map<String, Object>> readAllTopic() {
        return forumService.findAllTopic();
    }

    @ZeroSecurity
    @GetMapping("/topic/read-my")
    public List<Map<String, Object>> readAllMyTopic(){
        return forumService.readAllMyTopic();
    }

    // Under this comment place the Post Controller

    @ZeroSecurity
    @PostMapping("/post/create/{topicID}")
    public BaseResponse createPost(@RequestBody ForumPost forumPost, @PathVariable int topicID) {
        return forumService.createPost(forumPost, topicID);
    }

}
