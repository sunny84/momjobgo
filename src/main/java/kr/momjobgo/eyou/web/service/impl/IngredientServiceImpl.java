package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.IngredientEntity;
import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeIngredientMapEntity;
import kr.momjobgo.eyou.web.jpa.repository.IngredientCategoryRepository;
import kr.momjobgo.eyou.web.jpa.repository.IngredientRepository;
import kr.momjobgo.eyou.web.jpa.repository.RecipeIngredientMapRepository;
import kr.momjobgo.eyou.web.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RecipeIngredientMapRepository recipeIngredientMapRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientCategoryRepository ingredientCategoryRepository, RecipeIngredientMapRepository recipeIngredientMapRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.recipeIngredientMapRepository = recipeIngredientMapRepository;
    }

    @Override
    public List<IngredientCategoryEntity> joinCategory() {
        return ingredientCategoryRepository.findAll();
    }

    @Override
    public List<Long> joinRecipeMap(List<Long> Ids) { return recipeIngredientMapRepository.findByIngredientIds(Ids, Long.valueOf(Ids.size())); }

}
