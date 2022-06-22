package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import kr.momjobgo.eyou.web.jpa.entity.TestEntity;

import java.util.List;

public interface RecipeService {
    List<RecipeEntity> joinIngredient();
    List<RecipeEntity> findByPeriod(Long period);
}
