package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeIngredientMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeIngredientMapRepository extends JpaRepository<RecipeIngredientMapEntity, Long> {

    @Query("select m.id from RECIPE_INGREDIENT_MAP m where m.ingredientId in ( :Ids ) group by m.recipeId having count(distinct m.ingredientId) = :cnt")
    List<Long> findByIngredientIds(@Param("Ids") List<Long> Ids, @Param("cnt") Long cnt);

//    List<RecipeIngredientMapEntity> findByIngredientIds(@Param("Ids") List<Long> Ids);
}
