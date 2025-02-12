package kory.spring.com.bekoryfurniture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "invalidated_token")
public class InvalidatedToken {

    @Id
    private String id;

    private Date expiryTime;
}
