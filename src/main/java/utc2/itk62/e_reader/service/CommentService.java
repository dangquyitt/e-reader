package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long userId, Long bookId, String content);

    boolean deleteComment(Long id);

    Comment updateComment(Long userId, Long commentId, String content);

    List<Comment> getCommentByUser(Long id);

}
