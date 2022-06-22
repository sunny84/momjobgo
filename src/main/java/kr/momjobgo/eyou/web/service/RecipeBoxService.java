package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.TestEntity;

import java.util.List;

public interface RecipeBoxService {
    List<RecipeBoxEntity> joinRecipe();

    List<RecipeBoxEntity> getAll();
    RecipeBoxEntity getById(Long id);
    RecipeBoxEntity insertRecipeBoxName(String name);
    RecipeBoxEntity insertRecipeBox(RecipeBoxEntity entity);
    RecipeBoxEntity updateRecipeBoxById(Long id, RecipeBoxEntity entity);
    RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity);
    String deleteRecipeBoxById(Long id);
    String deleteRecipeBoxAll();
    List<RecipeBoxEntity> findByName(String name);
    List<RecipeBoxEntity> findByNameContains(String name);
}
