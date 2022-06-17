package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;
import kr.momjobgo.eyou.web.jpa.entity.IngredientEntity;

import java.util.List;

public interface IngredientService {
    List<IngredientCategoryEntity> joinCategory();

//    List<IngredientEntity> getAll();

}
