package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;

import java.util.List;

public interface IngredientCategoryService {
    List<IngredientCategoryEntity> getAll();

    IngredientCategoryEntity getIngredientCategory(Long id);

    IngredientCategoryEntity insertIngredientCategory(String title);

    IngredientCategoryEntity insertIngredientCategoryEntity(IngredientCategoryEntity entity);

    IngredientCategoryEntity updateIngredientCategory(IngredientCategoryEntity entity);

    String deleteIngredientCategory(Long id);

    List<IngredientCategoryEntity> findByTitle(String title);

    List<IngredientCategoryEntity> findByTitles(String title);
}
