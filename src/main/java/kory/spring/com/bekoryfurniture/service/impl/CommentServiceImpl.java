package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.CommentDTO;
import kory.spring.com.bekoryfurniture.dto.request.CommentRequestUpdate;
import kory.spring.com.bekoryfurniture.entity.Comment;
import kory.spring.com.bekoryfurniture.entity.Customer;
import kory.spring.com.bekoryfurniture.entity.Products;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.CommentRepo;
import kory.spring.com.bekoryfurniture.repository.CustomerRepo;
import kory.spring.com.bekoryfurniture.repository.ProductRepo;
import kory.spring.com.bekoryfurniture.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepo commentRepo;
    private ModelMapper modelMapper;
    private ProductRepo productRepo;
    private CustomerRepo customerRepo;

    @Override
    @Transactional
    public CommentDTO createNewComment(CommentDTO request) {
        if (request.getProductId() == 0 || request.getCustomerId() == 0) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_ID_OR_CUSTOMER_ID);
        }
        Products productEntity = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        Customer customerEntity = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CUSTOMER));

        Comment commentEntity = new Comment();
        commentEntity.setCustomerName(customerEntity.getName());
        commentEntity.setImage(customerEntity.getImage());
        commentEntity.setCommentContent(request.getCommentContent());
        commentEntity.setCustomerId(request.getCustomerId());
        commentEntity.setCreatedAt(getCurrentDate());
        commentEntity.setProduct(productEntity);
        commentEntity.setEdit(false);
        commentRepo.save(commentEntity);

        Set<Comment> listComment = productEntity.getListComment();
        listComment.add(commentEntity);
        productEntity.setListComment(listComment);
        productRepo.save(productEntity);

        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    @Override
    @Transactional
    public List<CommentDTO> getCommentsByProductID(Integer productId) {
        List<Comment> listCommentEntity = commentRepo.findByProduct_Id(productId);

        return listCommentEntity.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO updateComment(CommentRequestUpdate request) {
        Comment commentEntity = commentRepo.findById(request.getCommentId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMMENT));
        if (commentEntity.getCustomerId() != request.getCustomerId()){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        commentEntity.setCommentContent(request.getCommentContent());
        commentEntity.setEdit(true);
        commentRepo.save(commentEntity);

        return modelMapper.map(commentEntity, CommentDTO.class);
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId, Integer customerId) {
        Comment commentEntity = commentRepo.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMMENT));

        if (commentEntity.getCustomerId() != customerId){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        commentRepo.deleteById(commentId);

    }
}
