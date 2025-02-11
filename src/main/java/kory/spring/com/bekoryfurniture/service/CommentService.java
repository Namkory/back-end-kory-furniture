package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.CommentDTO;
import kory.spring.com.bekoryfurniture.dto.request.CommentRequestUpdate;

import java.util.List;

public interface CommentService {

    CommentDTO createNewComment(CommentDTO request);

    List<CommentDTO> getCommentsByProductID(Integer productId);

    CommentDTO updateComment (CommentRequestUpdate request);

    void deleteComment(Integer commentId, Integer customerId);
}
