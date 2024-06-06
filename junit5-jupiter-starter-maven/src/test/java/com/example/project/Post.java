package com.example.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Post {
    private int postID;
    private String postTitle;
    private String postBody;
    private String[] postTags;
    private String postType;
    private String postEmergency;
    // private int commentID;

    private ArrayList<String> postComments = new ArrayList<>();

    // Valid types and emergencies for posts
    private static final String[] VALID_TYPES = {"Very Difficult", "Difficult", "Easy"};
    private static final String[] VALID_EMERGENCIES = {"Immediately Needed", "Highly Needed", "Ordinary"};

    // Constructor to initialise a Post object
    public Post(int postID, String postTitle, String postBody, String[] postTags, String postType, String postEmergency) {
        this.postID = postID;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postTags = postTags;
        this.postType = postType;
        this.postEmergency = postEmergency;
    }

    Post(int postID, String postType, String postEmergency) {
        this.postID = postID;
        this.postType = postType;
        this.postEmergency = postEmergency;
    }
    

    // Method to add a post after validation
    public boolean addPost() {
        // Validate post title
        if (!validatePostTitle()) {
            System.out.println("Validation failed: Invalid post title." + "\n");
            return false;
        }
        // Validate post body
        if (!validatePostBody()) {
            System.out.println("Validation failed: Invalid post body."+ "\n");
            return false;
        }
        // Validate post tags
        if (!validatePostTags()) {
            System.out.println("Validation failed: Invalid post tags."+ "\n");
            return false;
        }
        // Validate post type and emergency combination
        if (!validatePostTypeAndEmergency()) {
            System.out.println("Validation failed: Invalid post type or emergency."+ "\n");
            return false;
        }

        // Write the post to a file if all validations pass
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("post.txt", true))) {
            writer.write(toString());
            writer.newLine();
            System.out.println("Post ID: " + this.postID + " - Post added successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate the post title
    private boolean validatePostTitle() {
        if (postTitle.length() < 10 || postTitle.length() > 250) {
            System.out.println("Post title length validation failed.");
            return false;
        }
        String firstFive = postTitle.substring(0, 5);
        if (firstFive.matches("[^a-zA-Z].*")) {
            System.out.println("Post title first five characters validation failed.");
            return false;
        }
        return true;
    }

    // Validate the post body based on the post type
    private boolean validatePostBody() {
        if (postType.equals("Very Difficult") || postType.equals("Difficult")) {
            if (postBody.length() < 300) {
                System.out.println("Post body length validation failed for 'Very Difficult' or 'Difficult'.");
                return false;
            }
        } else {
            if (postBody.length() < 250) {
                System.out.println("Post body length validation failed for Easy difficulty.");
                return false;
            }
        }
        return true;
    }

    // Validate the post tags
    private boolean validatePostTags() {
        if (postTags.length < 2 || postTags.length > 5) {
            System.out.println("Post tags count validation failed.");
            return false;
        }
        for (String tag : postTags) {
            if (!tag.equals(tag.toLowerCase()) || tag.length() < 2 || tag.length() > 10) {
                System.out.println("Post tag validation failed for tag: " + tag);
                return false;
            }
        }
        if (postType.equals("Easy") && postTags.length > 3) {
            System.out.println("Post tags count validation failed for 'Easy': Too many tags!!.");
            return false;
        }
        return true;
    }

    // Validate the combination of post type and emergency
    private boolean validatePostTypeAndEmergency() {
        if (!Arrays.asList(VALID_TYPES).contains(postType) || !Arrays.asList(VALID_EMERGENCIES).contains(postEmergency)) {
            System.out.println("Post type or emergency validation failed.");
            return false;
        }
        if (postType.equals("Easy") && (postEmergency.equals("Immediately Needed") || postEmergency.equals("Highly Needed"))) {
            System.out.println("'Easy' post cannot have 'Immediately Needed' or 'Highly Needed' status.");
            return false;
        }
        if ((postType.equals("Very Difficult") || postType.equals("Difficult")) && postEmergency.equals("Ordinary")) {
            System.out.println("'Very Difficult' or 'Difficult' post cannot have 'Ordinary' status.");
            return false;
        }
        return true;
    }

    // Method to add a comment to a post after validation
    public boolean addComment(String comment) {
        if (!validateComment(comment)) {
            return false;
        }

        if (!postExists(this.postID)) {
            System.out.println("Post ID does not exist.");
            // System.out.println(this.postID);
            return false;
        }

        postComments.add(comment);

        // Write the comment to a file if validation passes
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("comment.txt", true))) {
            writer.write("Post ID: " + this.postID + " - Comment: " + comment);
            writer.newLine();
            System.out.println("Post ID: " + this.postID + " - New comment created successfully");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate the comment
    private boolean validateComment(String comment) {
        String[] words = comment.split(" ");
        if (words.length < 4 || words.length > 10) {
            System.out.println("Comment word count validation failed.");
            return false;
        }
        if (!Character.isUpperCase(words[0].charAt(0))) {
            System.out.println("Comment first word uppercase validation failed.");
            return false;
        }
        if (postComments.size() >= 5 || (postType.equals("Easy") && postComments.size() >= 3) || (postEmergency.equals("Ordinary") && postComments.size() >= 3)) {
            System.out.println("Too many comments - validation failed.");
            return false;
        }
        return true;
    }

        private boolean postExists(int postID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("post.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Post ID: " + postID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // String representation of a post
    @Override
    public String toString() {
        return "Post ID: " + postID + ", Title: " + postTitle + ", Body: " + postBody + ", Tags: " 
        + String.join(", ", postTags) + ", Type: " + postType + ", Emergency: " + postEmergency;
    }


    public int getpostID() {
       return this.postID;
    }

    String getPostType() {
        return this.postType;
    }
}
