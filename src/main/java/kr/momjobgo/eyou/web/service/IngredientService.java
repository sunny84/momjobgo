package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;

import java.util.List;

public interface IngredientService {
    List<IngredientCategoryEntity> joinCategory();

}
