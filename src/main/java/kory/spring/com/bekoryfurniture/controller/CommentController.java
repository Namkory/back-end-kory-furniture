package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.CommentDTO;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.CommentRequestUpdate;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    ApiResponse<CommentDTO> createNewComment(@RequestBody @Valid CommentDTO request){
        CommentDTO response = commentService.createNewComment(request);
        ApiResponse<CommentDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getAllCommentByProductId(@RequestParam Integer productId) {
        List<CommentDTO> listComment = commentService.getCommentsByProductID(productId);
        return new ResponseEntity<>(listComment, HttpStatus.OK);
    }

    @PutMapping
    ApiResponse<CommentDTO> updateComment(@RequestBody @Valid CommentRequestUpdate request){
        CommentDTO response = commentService.updateComment(request);
        ApiResponse<CommentDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @DeleteMapping()
    ApiResponse<?> deleteComment(
            @RequestParam Integer commentId,
            @RequestParam Integer customerId
    ){
        commentService.deleteComment(commentId, customerId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }
}
