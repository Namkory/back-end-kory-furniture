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
@Table(name = "customers")
public class Customer extends Users{

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "email")
    private String email;

    @Column(name = "dob")
    private String dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "lastPurchaseDate")
    private String lastPurchaseDate;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "created_at")
    private String createdAt;
}
