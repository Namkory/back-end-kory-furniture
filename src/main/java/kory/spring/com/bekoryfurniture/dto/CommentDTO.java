package kory.spring.com.bekoryfurniture.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private int id;

    private String customerName;

    private String image;

    @NotBlank(message = "COMMENT_CONTENT_REQUIRED")
    private String commentContent;

    private boolean isEdit;

    private String createdAt;

    private int productId;

    private int customerId;
}
