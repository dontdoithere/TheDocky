package ca.group6.it.thedocky;

public class Review {

    private String postID;
    private String posterID;
    private float starCount;
    private String commentText;
    private String postDate;

    public Review() {
    }

    public Review(String postID, String posterID, float starCount, String commentText, String postDate) {
        this.postID = postID;
        this.posterID = posterID;
        this.starCount = starCount;
        this.commentText = commentText;
        this.postDate = postDate;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public float getStarCount() {
        return starCount;
    }

    public void setStarCount(float starCount) {
        this.starCount = starCount;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
