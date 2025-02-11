package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestUpdate {

    private int commentId;

    private int customerId;

    @NotBlank(message = "COMMENT_CONTENT_REQUIRED")
    private String commentContent;
}
