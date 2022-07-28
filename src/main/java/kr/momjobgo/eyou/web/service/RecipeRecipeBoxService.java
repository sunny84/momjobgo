package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeRecipeBoxEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecipeRecipeBoxService {
    List<RecipeRecipeBoxEntity> getAll();
    RecipeRecipeBoxEntity getRecipeRecipeBox(Long id);
    List<RecipeRecipeBoxEntity> getRecipeRecipeBoxByUserId();
    List<Map<String, Object>> findByRecipeContents(Long boxId);
    List<RecipeRecipeBoxEntity> findByRecipeBoxId(Long boxId);
    List<RecipeRecipeBoxEntity> findRecipesByRecipeBoxId(Long boxId);

    RecipeRecipeBoxEntity insertRecipeBox(Long boxId, Long recipeId);
    RecipeRecipeBoxEntity moveRecipeBox(Long fromBoxId, Long recipeId, Long toBoxId);
    String deleteRecipeRecipeBox(Long id);
    Object deleteRecipe(Long id, Long recipeId);
}
