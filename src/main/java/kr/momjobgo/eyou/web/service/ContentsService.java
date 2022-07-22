package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.ContentsEntity;

import java.util.List;
import java.util.Map;

public interface ContentsService {
    List<ContentsEntity> getAll();
    ContentsEntity getById(Long id);
    List<ContentsEntity> getByWriter(Long writer);

    Map<String, String> getContentsByRecipeId(Long recipeId);

    List<ContentsEntity> getByUserId();
}
