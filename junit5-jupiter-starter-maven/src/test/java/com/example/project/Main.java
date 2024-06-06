package com.example.project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Display options
            System.out.println("Welcome to the Post Management System");
            System.out.println("1. Add Post");
            System.out.println("2. Add Comment");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
                
            // Read user input
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Process user choice
            switch (choice) {
                case 1:
                    addPost(scanner);
                    break;
                case 2:
                    addComment(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
         // Close scanner and exit
        scanner.close();
        System.out.println("Thank you for using the Post Management System.");
    }
     // Prompt user for post details
    private static void addPost(Scanner scanner) {
        System.out.print("Enter Post ID: ");
        int postID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Post Title: ");
        String postTitle = scanner.nextLine();

        System.out.print("Enter Post Body: ");
        String postBody = scanner.nextLine();

        System.out.print("Enter Tags (comma-separated): ");
        String[] postTags = scanner.nextLine().split(",");

        System.out.print("Enter Post Type (Very Difficult, Difficult, Easy): ");
        String postType = scanner.nextLine();

        System.out.print("Enter Post Emergency (Immediately Needed, Highly Needed, Ordinary): ");
        String postEmergency = scanner.nextLine();

        Post post = new Post(postID, postTitle, postBody, postTags, postType, postEmergency);
        post.addPost();


    }

    private static void addComment(Scanner scanner) {
        System.out.print("Enter Post ID to comment on: ");
        int postID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();
        
        Comment.addCommentToFile(postID, comment);
    }    
}

