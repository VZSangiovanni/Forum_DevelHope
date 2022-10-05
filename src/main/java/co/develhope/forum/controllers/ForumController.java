package co.develhope.forum.controllers;

import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.PostDTO;
import co.develhope.forum.dto.response.TopicDTO;
import co.develhope.forum.dto.response.UpdateTopicDTO;
import co.develhope.forum.model.ForumCategory;
import co.develhope.forum.model.ForumPost;
import co.develhope.forum.model.ForumTopic;
import co.develhope.forum.services.CustomUserService;
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

    @Autowired
    private CustomUserService customUserService;

    /**
     * CATEGORY CRUD
     */

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @PostMapping("/category/create")
    public BaseResponse createCategory(@RequestBody ForumCategory forumCategory) {
        return forumService.createCategory(forumCategory);
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

    /**
     * TOPIC CRUD
     */

    @ZeroSecurity
    @PostMapping("/topic/create/{categoryTitle}")
    public BaseResponse createTopic(@RequestBody ForumTopic forumTopic, @PathVariable String categoryTitle) {
        return forumService.createTopic(forumTopic, categoryTitle);
    }

    @RoleSecurity(value = {"ROLE_MOD", "ROLE_ADMIN", "ROLE_FOUNDER"})
    @DeleteMapping("/topic/delete/{topicID}")
    public BaseResponse deleteTopic(@PathVariable int topicID) {
        return forumService.deleteTopic(topicID);
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/topic/delete/allTopic")
    public void deleteAllTopics() {
        forumService.deleteAllTopics();
    }

    @ZeroSecurity
    @PutMapping("/topic/update-topic-title/{topicID}")
    public BaseResponse updateTopicTitle(@RequestBody UpdateTopicDTO updateTopicDTO, @PathVariable int topicID) {
        return forumService.updateTopicTitle(updateTopicDTO, topicID);
    }

    @ZeroSecurity
    @PutMapping("/topic/update-topic-text/{topicID}")
    public BaseResponse updateTopicText(@RequestBody UpdateTopicDTO updateTopicDTO, @PathVariable int topicID) {
        return forumService.updateTopicText(updateTopicDTO, topicID);
    }

    @ZeroSecurity
    @PutMapping("/topic/update/title/{topicID}")
    public BaseResponse userUpdateTopicTitle(@RequestBody TopicDTO topicDTO, @PathVariable int topicID) {
        return forumService.userUpdateTopicTitle(topicDTO, topicID);
    }

    @ZeroSecurity
    @PutMapping("/topic/update/text/{topicID}")
    public BaseResponse userUpdateTopicText(@RequestBody TopicDTO topicDTO, @PathVariable int topicID) {
        return forumService.userUpdateTopicText(topicDTO, topicID);
    }

    @RoleSecurity(value = {"ROLE_FOUNDER", "ROLE_MOD", "ROLE_ADMIN"})
    @PutMapping("/topic/change-topic-category/{topicID}")
    public BaseResponse changeTopicCategory(@RequestBody TopicDTO topicDTO, @PathVariable int topicID) {
        return forumService.changeTopicCategory(topicDTO, topicID);
    }

    @ZeroSecurity
    @GetMapping("/topic/read-all")
    public List<Map<String, Object>> readTopics() {
        return forumService.findAllTopics();
    }

    @Deprecated
    @ZeroSecurity
    @GetMapping("/deprecated_3/topic/read-all")
    public List<Map<String, Object>> readAllTopic() {
        return forumService.findAllTopic();
    }

    @ZeroSecurity
    @GetMapping("/topic/read-by-category/{categoryTitle}")
    public List<Map<String, Object>> readTopicsByCategory(@PathVariable String categoryTitle) {
        return forumService.findAllTopicsByCategory(categoryTitle);
    }

    @Deprecated
    @ZeroSecurity
    @GetMapping("/deprecated_4/topic/read-by-category/{categoryTitle}")
    public List<Map<String, Object>> findAllTopicByCategoryTitle(@PathVariable String categoryTitle) {
        return forumService.findAllTopicByCategoryTitle(categoryTitle);
    }

    @ZeroSecurity
    @GetMapping("/topic/read-all-by-user/{userName}")
    public List<Map<String, Object>> readAllTopicByUser(@PathVariable String userName) {
        return forumService.findAllTopicByUser(userName);
    }

    @ZeroSecurity
    @GetMapping("/topic/read-my-topics")
    public List<Map<String, Object>> readAllMyTopic() {
        return forumService.findMyTopics();
    }

    @Deprecated
    @ZeroSecurity
    @GetMapping("/deprecated_2/read-my-topics/{id}")
    public List<Map<String, Object>> readMyTopics(@PathVariable int id) {
        return forumService.readAllMyTopic();
    }

    /**
     * CRUD POST
     */

    @ZeroSecurity
    @PostMapping("/post/create/{topicID}")
    public BaseResponse createPost(@RequestBody ForumPost forumPost, @PathVariable int topicID) {
        return forumService.createPost(forumPost, topicID);
    }

    @ZeroSecurity
    @PutMapping("/post/update-post-text/{postID}")
    public BaseResponse postUpdateText(@RequestBody PostDTO postDTO, @PathVariable int postID) {
        return forumService.postUpdateText(postDTO, postID);
    }

    @RoleSecurity(value = {"ROLE_FOUNDER", "ROLE_MOD", "ROLE_ADMIN"})
    @PutMapping("/post/change-post-topic/{postID}")
    public BaseResponse changePostTopic(@RequestParam int topicID, @PathVariable int postID) {
        return forumService.changePostTopic(topicID, postID);
    }

    @RoleSecurity(value = {"ROLE_MOD", "ROLE_ADMIN", "ROLE_FOUNDER"})
    @DeleteMapping("/post/delete/{postID}")
    public void deletePostByID(@PathVariable int postID) {
        forumService.deletePost(postID);
    }

    @RoleSecurity(value = {"ROLE_FOUNDER"})
    @DeleteMapping("/post/delete/allPost")
    public void deleteAllPost() {
        forumService.deleteAllPosts();
    }

    @ZeroSecurity
    @DeleteMapping("/post/deleteMyPost/{postId}")
    public BaseResponse deleteMyPost(@PathVariable int postId) {
        return forumService.deleteThisPost(postId);
    }

    @ZeroSecurity
    @GetMapping("/post/read-all")
    public List<Map<String, Object>> readPost() {
        return forumService.readAllPosts();
    }

    @Deprecated
    @ZeroSecurity
    @GetMapping("/deprecated_1/post/read-all")
    public List<Map<String, Object>> readAllPost() {
        return forumService.findAllPost();
    }

    @ZeroSecurity
    @GetMapping("/post/read-by-topic/{id}")
    public List<Map<String, Object>> readPostsByTopic(@PathVariable int id) {
        return forumService.findAllPostsByTopic(id);
    }

    @Deprecated
    @ZeroSecurity
    @GetMapping("/deprecated_5/post/read-by-topic/{topicID}")
    public List<Map<String, Object>> readAllPostByTopicID(@PathVariable int topicID) {
        return forumService.findAllPostByTopicID(topicID);
    }

    @ZeroSecurity
    @GetMapping("/post/read-my")
    public List<Map<String, Object>> readAllMyPost() {
        return forumService.readAllMyPost();
    }

    @ZeroSecurity
    @GetMapping("/post/read-all-by-user/{userName}")
    public List<Map<String, Object>> readAllPostByUser(@PathVariable String userName) {
        return forumService.findAllPostByUser(userName);
    }

    @ZeroSecurity
    @GetMapping("/post/read-my-posts")
    public List<Map<String, Object>> readMyPosts() {
        return forumService.findMyPosts();
    }
}