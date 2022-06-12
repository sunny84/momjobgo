package kr.momjobgo.eyou.web.jpa.entity;

import kr.momjobgo.eyou.web.jpa.entity.base.BaseTimeEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity(name = "RECIPE")
@Data
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "recipe")
    private List<SubscribeRecipeEntity> subscribes = new ArrayList<>();
}
