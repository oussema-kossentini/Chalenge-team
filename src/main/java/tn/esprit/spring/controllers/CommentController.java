package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.SentimentAnalysisRequest;
import tn.esprit.spring.services.ICommentService;
import tn.esprit.spring.services.IPublicationService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    ICommentService commentService;
    IPublicationService publicationService;
    @PostMapping("add/comment")
    public Comment addingComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    @GetMapping("/retrieve-all-comments")
    public List<Comment> getChambres() {
        List<Comment> listComments = commentService.retrieveAllComments();
        return listComments;
    }
    @DeleteMapping("/remove-comments/{comment-id}")
    public void removeComment(@PathVariable("comment-id") String chId) {

        commentService.removeComment(chId);
    }



    @PutMapping("/modify-Comment")
    public ResponseEntity<Comment> modifyComment(@RequestBody Comment comment){

        Comment updatedUser = commentService.modifyComment(comment); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
    }
    @PostMapping("/{publicationId}/comments")
    public Comment addCommentToPublication(@PathVariable String publicationId, @RequestBody Comment comment) {

        return commentService.addCommentToPublication(publicationId, comment);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<List<Comment>> getCommentsForPublication(@PathVariable String publicationId) {
        List<Comment> comments = commentService.getCommentsForPublication(publicationId);
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/get")
    public Publication gettingComment(@RequestParam("Comment-id") String idPublication){
        return publicationService.getPublicationById(idPublication);
    }
    @PostMapping("/analyze-sentiment1")
    public ResponseEntity<String> analyzeSentiment(@RequestBody String sentence) {
        try {
            // Construct the command with double quotes around the file path
            String command = "C:/Python312/python.exe \"C:/Users/ASUS/Downloads/BAKKKK/Chalenge-team-forummbackk/src/main/java/tn/esprit/spring/controllers/sentiment.py\" " + sentence;

            // Execute Python script and pass input sentence as command-line argument
            Process process = Runtime.getRuntime().exec(command);

            // Read output from Python script
            InputStream inputStream = process.getInputStream();
            String output = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            inputStream.close();

            // Read any errors from the Python script
            InputStream errorStream = process.getErrorStream();
            String errorOutput = StreamUtils.copyToString(errorStream, StandardCharsets.UTF_8);
            errorStream.close();

            // Wait for Python script to finish
            int exitCode = process.waitFor();

            // Check for errors in Python script execution
            if (exitCode != 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error analyzing sentiment: " + errorOutput);
            }

            // Return sentiment prediction received from Python script
            return ResponseEntity.ok(output.trim());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error analyzing sentiment: " + e.getMessage());
        }
    }

    @PostMapping("/analyze-sentiment")
    public ResponseEntity<Map<String, String>> analyzeSentiment(@RequestBody SentimentAnalysisRequest request) {
        try {
            List<String> command = Arrays.asList(
                    "C:/Python312/python.exe",
                    "C:/Users/ASUS/Downloads/BAKKKK/Chalenge-team-forummbackk/src/main/java/tn/esprit/spring/controllers/sentiment.py",
                    request.getSentence());

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Collect output and error in strings
            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            String errorOutput = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8).trim();

            // Wait for the process to exit
            int exitCode = process.waitFor();

            // Check for errors in Python script execution
            if (exitCode != 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", "Error analyzing sentiment: " + errorOutput));
            }

            // Return sentiment prediction received from Python script
            return ResponseEntity.ok(Collections.singletonMap("result", output));
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error analyzing sentiment: " + e.getMessage()));
        }
    }
}
