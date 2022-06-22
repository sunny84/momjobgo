package kr.momjobgo.eyou.web.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import kr.momjobgo.eyou.web.jpa.entity.base.BaseTimeEntity;
import lombok.Data;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "RECIPEBOX")
public class RecipeBoxEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_default")
    private Boolean isDefault;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RECIPE_RECIPEBOX_MAP",                       // 조인 테이블 명
            joinColumns = @JoinColumn(name = "recipe_box_id"),      // 현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "recipe_id")    // 반대방향 엔티티를 참조하는 외래키
    )
    private List<RecipeEntity> recipeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "recipeBox")
    private Set<RecipeEntity> recipes = new HashSet<>();

    public void add(RecipeEntity recipe) {
        recipe.setRecipeBox(this);
        this.recipes.add(recipe);
    }
}
