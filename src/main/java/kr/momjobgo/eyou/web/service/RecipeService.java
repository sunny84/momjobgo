package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;

import java.util.List;

public interface RecipeService {
    List<RecipeEntity> getAll();
    RecipeEntity getRecipe(Long id);
    RecipeEntity insertRecipeEntity(RecipeEntity entity);
    RecipeEntity updateRecipe(RecipeEntity entity);
    String deleteRecipe(Long id);
}
