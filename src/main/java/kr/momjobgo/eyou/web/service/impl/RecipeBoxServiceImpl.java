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

    @Override
    public List<RecipeBoxEntity> getAll() {
        return null;
    }

    @Override
    public RecipeBoxEntity getById(Long id) {
        return null;
    }

    @Override
    public RecipeBoxEntity insertRecipeBoxName(String name) {
        return null;
    }

    @Override
    public RecipeBoxEntity insertRecipeBox(RecipeBoxEntity entity) {
        return null;
    }

    @Override
    public RecipeBoxEntity updateRecipeBoxById(Long id, RecipeBoxEntity entity) {
        return null;
    }

    @Override
    public RecipeBoxEntity updateRecipeBoxNameById(Long id, String name) {
        return null;
    }

    @Override
    public RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity) {
        return null;
    }

    @Override
    public String deleteRecipeBoxById(Long id) {
        return null;
    }

    @Override
    public String deleteRecipeBoxAll() {
        return null;
    }

    @Override
    public List<RecipeBoxEntity> findByName(String name) {
        return null;
    }

    @Override
    public List<RecipeBoxEntity> findByNameContains(String name) {
        return null;
    }

    @Override
    public RecipeBoxEntity findByIsDefault(Boolean isDefault) {
        return null;
    }
}
