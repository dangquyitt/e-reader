package utc2.itk62.e_reader.service.impl;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.book.BookDetail;

import java.util.List;

public interface CommentService {

    Comment createComment(Long userId, Long bookId, String content);

    boolean deleteComment(Long id);

    Comment updateComment(Long userId, Long commentId, String content);

    List<Comment> getCommentByUser(Long id);

}
