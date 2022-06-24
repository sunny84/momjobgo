package kr.momjobgo.eyou.web.jpa.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "RECIPE_INGREDIENT_MAP")
@Data
//@NamedQuery(
//        name = "RecipeIngredientMapEntity.findByIngredientIds",
//        query = "select m.id from RECIPE_INGREDIENT_MAP m where m.ingredientId in ( :Ids )"
//)
public class RecipeIngredientMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Column(name = "recipe_id")
    private Long recipeId;

}