package com.example.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Comment {
    // Method to add a comment to the comment.txt file
    public static boolean addCommentToFile(int postID, String comment) {
        // Read post details from post.txt
        Post post = readPostFromFile(postID);
        if (post != null) {
            // Check if the comment limit is exceeded
            if (isCommentLimitExceeded(post)) {
                System.out.println("Comment limit exceeded. Comment not added.");
                return false;
            }
            // Write the comment to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("comment.txt", true))) {
                writer.write("Post ID: " + postID + " - Comment: " + comment);
                writer.newLine();
                System.out.println("Post ID: " + postID + " - New comment created successfully");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Post not found with ID: " + postID);
            return false;
        }
    }

    // Read post details from post.txt
    private static Post readPostFromFile(int postID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("post.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Post ID: " + postID)) {
                    String postType = extractPostType(line);
                    String postEmergency = extractPostEmergency(line);
                    return new Post(postID, postType, postEmergency);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Extract post type from the line read from post.txt
private static String extractPostType(String line) {
    String[] parts = line.split(",");
    for (String part : parts) {
        if (part.trim().startsWith("Type:")) {
            return part.trim().substring(6);
        }
    }
    return null; // or throw an exception if the type is not found
}

// Extract post emergency from the line read from post.txt
private static String extractPostEmergency(String line) {
    String[] parts = line.split(",");
    for (String part : parts) {
        if (part.trim().startsWith("Emergency:")) {
            return part.trim().substring(11);
        }
    }
    return null; // or throw an exception if the emergency is not found
}
    // Check if the comment limit is exceeded based on post type
    private static boolean isCommentLimitExceeded(Post post) {
        String postType = post.getPostType();
        int commentLimit = (postType.equals("Easy")) ? 3 : 5;
        int existingCommentsCount = countExistingComments(post.getpostID());
        return existingCommentsCount >= commentLimit;
    }

    // Count existing comments for the given post ID
    private static int countExistingComments(int postID) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("comment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Post ID: " + postID)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
