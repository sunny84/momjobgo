package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "INGREDIENT")
@Data
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "key")
    private String key;

    @Column(name = "category_key")
    private Long categoryKey;

}