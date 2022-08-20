package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeRecipeBoxEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RecipeRecipeBoxService {
    List<RecipeRecipeBoxEntity> getAll();
    RecipeRecipeBoxEntity getRecipeRecipeBox(Long id);
    List<RecipeRecipeBoxEntity> getRecipeRecipeBoxByUserId();
    List<Map<String, Object>> findByRecipeContents(Long boxId, Pageable pageable);
    List<RecipeRecipeBoxEntity> findByRecipeBoxId(Long boxId);
    List<RecipeRecipeBoxEntity> findRecipesByRecipeBoxId(Long boxId);

    RecipeRecipeBoxEntity insertRecipeBox(Long boxId, Long recipeId);
    RecipeRecipeBoxEntity moveRecipeBox(Long fromBoxId, Long recipeId, Long toBoxId);
    String deleteRecipeRecipeBox(Long id);

    List<Map<String, Object>> getSubscribeList(Long userId, Long period);
    Object deleteRecipe(Long id, Long recipeId);

//    List<Map<String, Object>> findByRecipeContents(Long boxId, Pageable pageable);

    List<Map<String, Object>>  findByRecipeContentsByUserId();
    RecipeRecipeBoxEntity findByRecipeId(Long recipeId);
}
