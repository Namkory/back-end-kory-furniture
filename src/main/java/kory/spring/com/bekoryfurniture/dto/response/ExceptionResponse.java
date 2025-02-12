package kory.spring.com.bekoryfurniture.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

    private String message;
    private Integer code;
}
