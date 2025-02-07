package kory.spring.com.bekoryfurniture.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private int id;

    @NotBlank(message = "COMMENTER_NAME_REQUIRED")
    private String commenterName;

    @NotBlank(message = "IMAGE_REQUIRED")
    private String image;

    @NotBlank(message = "COMMENT_CONTENT_REQUIRED")
    private String commentContent;

    private String createdAt;

    private int productId;
}
