package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;

import java.util.List;

public interface RecipeService {
    List<RecipeEntity> findByFilter(Long period, Long timeTakenId, List<Long> Ids);
}
