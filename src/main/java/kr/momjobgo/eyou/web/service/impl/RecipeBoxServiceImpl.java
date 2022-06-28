package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeBoxServiceImpl implements RecipeBoxService {
    private final RecipeBoxRepository recipeBoxRepository;

    public RecipeBoxServiceImpl(RecipeBoxRepository recipeBoxRepository) { this.recipeBoxRepository = recipeBoxRepository; }

    @Override
    public List<RecipeBoxEntity> joinRecipe() { return recipeBoxRepository.findAll(); }

    @Override
    public List<RecipeBoxEntity> getAll() { return recipeBoxRepository.findAll(); }

    @Override
    public RecipeBoxEntity getById(Long id){

        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(id);

        if(recipeBoxEntity.isPresent()){
            return recipeBoxEntity.get();
        } else {
            return null;
        }
    }

    @Override
    public RecipeBoxEntity insertRecipeBoxName(String name) {
        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
        recipeBoxEntity.setName(name);
        return recipeBoxRepository.save(recipeBoxEntity);
    }

    @Override
    public RecipeBoxEntity insertRecipeBox(RecipeBoxEntity entity) { return recipeBoxRepository.save(entity); }

    @Override
    public RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity) {
        return recipeBoxRepository.save(entity);
    }

    @Override
    public String deleteRecipeBoxById(Long id) {
        if(recipeBoxRepository.findById(id).isPresent()) {
            recipeBoxRepository.deleteById(id);
            return "삭제성공";
        }
        else {
            return "아이디가 존재하지 않음";
        }
    }

    @Override
    public String deleteRecipeBoxAll() {
        return null;
    }

    @Override
    public List<RecipeBoxEntity> findByName(String name) {
        return recipeBoxRepository.findByName(name);
    }

    @Override
    public List<RecipeBoxEntity> findByNameContains(String name) {
        return recipeBoxRepository.findByNameContains(name);
    }

    @Override
    public RecipeBoxEntity findByIsDefault(Boolean isDefault) {
        return recipeBoxRepository.findByIsDefault(isDefault);
    }
}
