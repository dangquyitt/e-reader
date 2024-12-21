package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CommentFilter;
import utc2.itk62.e_reader.domain.model.OrderBy;

import java.util.List;

public interface CommentService {

    Comment createComment(Long userId, Long bookId, String content);

    boolean deleteComment(Long id, Long userId);

    Comment updateComment(Long userId, Long commentId, String content);

    List<Comment> getCommentByUser(CommentFilter commentFilter, OrderBy orderBy, Pagination pagination);

}
