package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.services.CustomUserService;
import co.develhope.forum.services.ForumService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private CustomUserService customUserService;

    @RoleSecurity(value = {"ROLE_USER"})
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

    @RoleSecurity(value = {"ROLE_MODERATOR"})
    @DeleteMapping("/topic/delete/{topicID}")
    public void deleteTopic (@PathVariable int topicID) {
        forumService.deleteTopic(topicID);
    }
    //TODO INTEGRATE

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/topic/delete/allTopic")
    public void deleteAllTopics(){
        forumService.deleteAllTopics();
    }
    //TODO INTEGRATE

    // Under this comment place the Post Controller

    @ZeroSecurity
    @PostMapping("/post/create/{topicID}")
    public BaseResponse createPost(@RequestBody ForumPost forumPost, @PathVariable int topicID) {
        return forumService.createPost(forumPost, topicID);
    }

    @RoleSecurity(value = {"ROLE_MODERATOR"})
    @DeleteMapping("/post/delete/{postID}")
    public void deleteSinglePost(@PathVariable int postID) {
        forumService.deletePost(postID);
    }
    //TODO INTEGRATE
    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/category/delete/allPost")
    public void deleteAllPost(){
        forumService.deleteAllPosts();
    }
    //TODO INTEGRATE
}
