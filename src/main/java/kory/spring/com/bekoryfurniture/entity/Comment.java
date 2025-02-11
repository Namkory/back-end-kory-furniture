package kory.spring.com.bekoryfurniture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_Id")
    private int customerId;

    @Column(name = "image")
    private String image;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "is_edit")
    private boolean isEdit;

    @Column(name = "created_at")
    private String createdAt;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Products product;
}
