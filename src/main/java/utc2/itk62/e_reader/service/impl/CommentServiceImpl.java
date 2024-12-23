package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CommentFilter;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.CommentRepository;
import utc2.itk62.e_reader.service.CommentService;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Long userId, Long bookId, String content) {
        return commentRepository.save(Comment.builder().content(content).bookId(bookId).userId(userId).build());
    }

    @Override
    public boolean deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.findByIdAndUserId(id, userId).orElseThrow(() ->
                new EReaderException("not found comment Id"));
        commentRepository.delete(comment);
        return !commentRepository.existsById(id);
    }

    @Override
    public Comment updateComment(Long userId, Long commentId, String content) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, userId).orElseThrow(() ->
                new EReaderException("not found by userId and commentId"));
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentByUser(CommentFilter commentFilter, OrderBy orderBy, Pagination pagination) {
        Specification<Comment> spec = Specification.where(null);
        if (commentFilter.getUserId() != null) {
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), commentFilter.getUserId())));
        }

        Sort sort = null;
        if (orderBy != null) {
            Sort.Direction direction = orderBy.getOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, orderBy.getField());
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize(), sort);
        Page<Comment> commentPage = commentRepository.findAll(spec, pageable);
        pagination.setTotal(commentPage.getTotalElements());
        return commentPage.toList();
    }

    @Override
    public Comment getCommentById(Long userId, Long commentId) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, userId).orElseThrow(() -> new EReaderException("not found comment Id"));
        return comment;
    }
}
