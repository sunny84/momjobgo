package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.IngredientEntity;
import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;
import kr.momjobgo.eyou.web.jpa.repository.IngredientCategoryRepository;
import kr.momjobgo.eyou.web.jpa.repository.IngredientRepository;
import kr.momjobgo.eyou.web.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientCategoryRepository ingredientCategoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
    }

    @Override
    public List<IngredientEntity> joinCategory() {
        return ingredientRepository.findAll();
    }

    // 모두 가져오기.
    @Override
    public List<IngredientEntity> getAll() {
        return ingredientRepository.findAll();
    }

}
