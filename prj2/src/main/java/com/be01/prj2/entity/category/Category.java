package com.be01.prj2.entity.category;

import com.be01.prj2.entity.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long categoryId;
    private String categoryName;

    @OneToMany(mappedBy = "category") // mappedBy 속성은 Product 엔터티에 있는 필드명을 나타냅니다.
    private List<Product> products;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
