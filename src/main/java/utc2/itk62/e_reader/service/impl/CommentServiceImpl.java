package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.Comment;
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
        return Comment.builder().content(content).bookId(bookId).userId(userId).build();
    }

    @Override
    public boolean deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
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
    public List<Comment> getCommentByUser(Long userId) {
        return commentRepository.findAllByUserId(userId);
    }
}
