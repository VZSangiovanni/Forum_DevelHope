package co.develhope.forum.exception;

public class ForumCategoryTitleAlreadyExistException extends RuntimeException{

    private String categoryTitle;

    public ForumCategoryTitleAlreadyExistException(String categoryTitle) {
        super("Category Title already exist");
        this.categoryTitle = categoryTitle;
    }
}
