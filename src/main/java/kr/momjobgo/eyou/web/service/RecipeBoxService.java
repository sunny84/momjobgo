package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;

import java.util.List;

public interface RecipeBoxService {
    List<RecipeBoxEntity> joinRecipe();

    List<RecipeBoxEntity> getAll();
    RecipeBoxEntity getById(Long id);
    RecipeBoxEntity insertRecipeBoxName(String name);
    RecipeBoxEntity insertRecipeBox();
    RecipeBoxEntity updateRecipeBox(Long id, String name);

    String deleteRecipeBoxById(Long id);

    String deleteRecipeBoxByName(String name);

    String deleteRecipeBoxAll();
    List<RecipeBoxEntity> findByName(String name);
    List<RecipeBoxEntity> findByNameContains(String name);
    RecipeBoxEntity findByIsDefault();

    List<RecipeBoxEntity> findByUserId(Long id);
}

