package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.ICommentService;
import tn.esprit.spring.services.IPublicationService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    ICommentService commentService;
    IPublicationService publicationService;
    UserRepository userRepository;
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @PostMapping("add/comment")
    public Comment addingComment(Authentication authentication, @RequestBody Comment comment){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return commentService.addComment(comment);

    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @GetMapping("/retrieve-all-comments")
    public List<Comment> getChambres(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Comment> listComments = commentService.retrieveAllComments();
        return listComments;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @DeleteMapping("/remove-comments/{comment-id}")
    public void removeComment(Authentication authentication,@PathVariable("comment-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        commentService.removeComment(chId);
    }


    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @PutMapping("/modify-Comment")
    public ResponseEntity<Comment> modifyComment(Authentication authentication,@RequestBody Comment comment){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Comment updatedUser = commentService.modifyComment(comment); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @PostMapping("/{publicationId}/comments")
    public Comment addCommentToPublication(Authentication authentication,@PathVariable String publicationId, @RequestBody Comment comment) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return commentService.addCommentToPublication(publicationId, comment);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @GetMapping("/{publicationId}")
    public ResponseEntity<List<Comment>> getCommentsForPublication(Authentication authentication,@PathVariable String publicationId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Comment> comments = commentService.getCommentsForPublication(publicationId);
        return ResponseEntity.ok(comments);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @GetMapping("/get")
    public Publication gettingComment(Authentication authentication,@RequestParam("Comment-id") String idPublication){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return publicationService.getPublicationById(idPublication);
    }

}