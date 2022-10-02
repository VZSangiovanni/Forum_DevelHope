package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.PostDTO;
import co.develhope.forum.dto.response.TopicDTO;
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
    public ForumCategory findCategoryByTitle(@PathVariable String categoryTitle) {
        return forumService.findCategoryByTitle(categoryTitle);
    }


    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/category/delete/all")
    public void deleteAllCategory() {
        forumService.deleteAllCategory();
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/category/delete/{categoryTitle}")
    public void deleteOneCategory(@PathVariable String categoryTitle) {
        forumService.deleteOneCategory(categoryTitle);
    }

    // Under this comment place the Topic Controller
    @ZeroSecurity
    @PostMapping("/topic/create/{categoryTitle}")
    public BaseResponse createTopic(@RequestBody ForumTopic forumTopic, @PathVariable String categoryTitle) {
        return forumService.createTopic(forumTopic, categoryTitle);
    }

    @ZeroSecurity
    @GetMapping("/read-my-topics/{id}")
    public List<Map<String, Object>> readMyTopics(@PathVariable int id) {
        return forumService.findMyTopics();
    }

    @ZeroSecurity
    @GetMapping("/read-all-topics")
    public List<Map<String, Object>> readTopics() {
        return forumService.findAllTopics();
    }

    @ZeroSecurity
    @GetMapping("/read-topics_by_category/{categoryTitle}")
    public List<Map<String, Object>> readTopicsByCategory(@PathVariable String categoryTitle) {
        return forumService.findAllTopicsByCategory(categoryTitle);
    }

    // Under this comment place the Post Controller

    @ZeroSecurity
    @PostMapping("/post/create/{topicID}")
    public BaseResponse createPost(@RequestBody ForumPost forumPost, @PathVariable int topicID) {
        return forumService.createPost(forumPost, topicID);


    }

     @ZeroSecurity
    @PutMapping("/topic/update/title/{topicID}")
    public BaseResponse userUpdateTopicTitle(@RequestBody TopicDTO topicDTO,@PathVariable int topicID){


        return forumService.userUpdateTopicTitle(topicDTO,topicID);

    }

    @ZeroSecurity
    @PutMapping("/topic/update/text/{topicID}")
    public BaseResponse userUpdateTopicText(@RequestBody TopicDTO topicDTO,@PathVariable int topicID){


        return forumService.userUpdateTopicText(topicDTO,topicID);

    }

    @ZeroSecurity
    @PutMapping("/post/update-post-text/{postID}")
    public BaseResponse postUpdateText(@RequestBody PostDTO postDTO,@PathVariable int postID){

        return forumService.postUpdateText(postDTO,postID);
    }


    @RoleSecurity(value = {"ROLE_FOUNDER","ROLE_MOD","ROLE_ADMIN"})
    @PutMapping("/topic/change-topic-category/{topicID}")
    public BaseResponse changeTopicCategory(@RequestBody TopicDTO topicDTO,@PathVariable int topicID){
     return forumService.changeTopicCategory(topicDTO,topicID);

    }

    @RoleSecurity(value = {"ROLE_FOUNDER","ROLE_MOD","ROLE_ADMIN"})
    @PutMapping("/post/change-post-topic/{postID}")
    public BaseResponse changePostTopic(@RequestParam int topicID,@PathVariable int postID){
       return  forumService.changePostTopic(topicID,postID);


    }







    @ZeroSecurity
    @GetMapping("/read-my-posts")
    public List<Map<String, Object>> readMyPosts() {
        return forumService.findMyPosts();
    }

    @ZeroSecurity
    @GetMapping("/read-all-posts")
    public List<Map<String, Object>> readPost() {
        return forumService.findAllPosts();
    }

    @ZeroSecurity
    @GetMapping("/read-post-by-topic/{id}")
    public List<Map<String, Object>> readPostsByTopic(@PathVariable int id) {
        return forumService.findAllPostsByTopic(id);
    }
}