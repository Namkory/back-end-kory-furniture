package kory.spring.com.bekoryfurniture.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kory.spring.com.bekoryfurniture.entity.Users;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto {

    private int id;
    private String fullName;
    private String email;
    private String gender;
    private String phoneNumber;
    private String address;
    private String note;
    private LocalDateTime orderDate;
    private BigDecimal totalMoney;
}
