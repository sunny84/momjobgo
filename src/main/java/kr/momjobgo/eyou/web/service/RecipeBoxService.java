package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;

import java.util.List;

public interface RecipeBoxService {
    List<RecipeBoxEntity> joinRecipe();
}
