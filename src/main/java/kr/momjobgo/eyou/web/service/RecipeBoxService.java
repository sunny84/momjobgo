package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;

import java.util.List;

public interface RecipeBoxService {
    List<RecipeBoxEntity> getAll();
    RecipeBoxEntity getById(Long id);
    RecipeBoxEntity insertRecipeBoxName(String name);
    RecipeBoxEntity insertRecipeBox(RecipeBoxEntity entity);
    RecipeBoxEntity updateRecipeBoxNameById(Long id, String name);
    RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity);
    String deleteRecipeBoxById(Long id);
    String deleteRecipeBoxAll();
    List<RecipeBoxEntity> findByName(String name);
    List<RecipeBoxEntity> findByNameContains(String name);
    RecipeBoxEntity findByIsDefault(Boolean isDefault);
}
