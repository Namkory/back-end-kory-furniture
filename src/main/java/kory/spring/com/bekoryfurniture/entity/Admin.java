package kory.spring.com.bekoryfurniture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "admins")
public class Admin extends Users{

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "dob")
    private String dob;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "created_at")
    private String createdAt;
}
