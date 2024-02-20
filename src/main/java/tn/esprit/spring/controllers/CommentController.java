package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.services.IClasseService;
import tn.esprit.spring.services.ICommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/comments")
public class CommentController {
    ICommentService commentService;
    @PostMapping("add/comment")
    public Comment addingComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    @GetMapping("/retrieve-all-comments")
    public List<Comment> getChambres() {
        List<Comment> listComments = commentService.retrieveAllComments();
        return listComments;
    }
    @DeleteMapping("/remove-Comment/{comment-id}")
    public void removeComment(@PathVariable("comment-id") String chId) {
        commentService.removeComment(chId);
    }
    @PutMapping("/modify-comment/{id}")
    public Comment modifyComment(@PathVariable String id,@RequestBody Comment c) {
        c.setIdComment(id);
        return commentService.addComment(c);

    }
}
