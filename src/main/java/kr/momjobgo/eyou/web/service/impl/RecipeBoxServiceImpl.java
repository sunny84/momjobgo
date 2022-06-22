package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeBoxServiceImpl implements RecipeBoxService {
    private final RecipeBoxRepository recipeBoxRepository;

    public RecipeBoxServiceImpl(RecipeBoxRepository recipeBoxRepository) { this.recipeBoxRepository = recipeBoxRepository; }

    @Override
    public List<RecipeBoxEntity> joinRecipe() { return recipeBoxRepository.findAll(); }
}
