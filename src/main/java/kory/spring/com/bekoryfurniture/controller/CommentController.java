package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.CommentDTO;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.CommentRequestUpdate;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/comment")
@Tag(name = "Comment Controller")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Add comment", description = "API create new comment")
    @PostMapping
    ApiResponse<CommentDTO> createNewComment(@RequestBody @Valid CommentDTO request){
        log.info("Request add comment, {}",
                request.toString());
        CommentDTO response = commentService.createNewComment(request);
        ApiResponse<CommentDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Get comment list by productId", description = "Return all comments of the product.")
    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getAllCommentByProductId(@RequestParam Integer productId) {
        log.info("Request get all comments of product");
        List<CommentDTO> listComment = commentService.getCommentsByProductID(productId);
        return new ResponseEntity<>(listComment, HttpStatus.OK);
    }

    @Operation(summary = "Update comment", description = "API update comment")
    @PutMapping
    ApiResponse<CommentDTO> updateComment(@RequestBody @Valid CommentRequestUpdate request){
        log.info("Request update comment, {}", request.toString());
        CommentDTO response = commentService.updateComment(request);
        ApiResponse<CommentDTO> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Delete comment", description = "API delete comment")
    @DeleteMapping()
    ApiResponse<?> deleteComment(
            @RequestParam Integer commentId,
            @RequestParam Integer customerId
    ){
        log.info("Request delete comment by commentId={}, customerId={}", commentId, customerId);
        commentService.deleteComment(commentId, customerId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }
}
