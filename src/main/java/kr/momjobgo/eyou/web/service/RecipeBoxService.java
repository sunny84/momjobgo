package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeRecipeBoxEntity;

import java.util.List;

public interface RecipeBoxService {
    List<RecipeBoxEntity> getAll();
    RecipeBoxEntity getRecipeBox(Long id);
    RecipeBoxEntity insertRecipeBox(String name);
    RecipeBoxEntity insertRecipeBoxEntity(RecipeBoxEntity entity);
    RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity);
    String deleteRecipeBox(Long id);
    List<RecipeBoxEntity> findByName(String name);
    List<RecipeBoxEntity> findByNameContains(String name);
//    List<recipeBoxEntity> findByUserId(UserEntity user);
    List<RecipeRecipeBoxEntity> findByRecipeBoxRecipes();
}
