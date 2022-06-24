package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeRepository;
import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeEntity> findAll() {
        return recipeRepository.findAll();
    }


    @Override
    public List<RecipeEntity> joinIngredient() {
        return null;
    }

    @Override
    public List<RecipeEntity> findByPeriod(Long period) {
        return recipeRepository.findByPeriod(period);
    }
}
