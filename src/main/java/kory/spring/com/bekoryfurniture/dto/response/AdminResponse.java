package kory.spring.com.bekoryfurniture.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponse {

    private int id;
    private String userName;
    private String name;
    private String image;
    private String dob;
    private String address;
    private String gender;
    private String phone;
    private String roles;
    private boolean isDelete;
    private String createdAt;
}
