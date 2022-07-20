package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;
import kr.momjobgo.eyou.web.jpa.entity.IngredientEntity;
import kr.momjobgo.eyou.web.jpa.repository.IngredientCategoryRepository;
import kr.momjobgo.eyou.web.jpa.repository.IngredientRepository;
import kr.momjobgo.eyou.web.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientCategoryRepository ingredientCategoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
    }

    @Override
    public List<IngredientCategoryEntity> joinCategory() {
        return ingredientCategoryRepository.findAll();
    }

}
