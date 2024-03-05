package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Publication;
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

}
