package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;
import kr.momjobgo.eyou.web.jpa.repository.IngredientCategoryRepository;
import kr.momjobgo.eyou.web.service.IngredientCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {
    private final IngredientCategoryRepository ingredientCategoryRepository;

    public IngredientCategoryServiceImpl(IngredientCategoryRepository ingredientCategoryRepository) {
        this.ingredientCategoryRepository = ingredientCategoryRepository;
    }

    @Override
    public List<IngredientCategoryEntity> getAll() { return ingredientCategoryRepository.findAll(); }

    @Override
    public IngredientCategoryEntity getIngredientCategory(Long id) {
        Optional<IngredientCategoryEntity> ingredientCategoryEntity = ingredientCategoryRepository.findById(id);

        return ingredientCategoryEntity.orElse(null);
    }

    @Override
    public IngredientCategoryEntity insertIngredientCategory(String title) {
        IngredientCategoryEntity ingredientCategoryEntity = new IngredientCategoryEntity();
        ingredientCategoryEntity.setTitle(title);

        return ingredientCategoryRepository.save(ingredientCategoryEntity);
    }

    @Override
    public IngredientCategoryEntity insertIngredientCategoryEntity(IngredientCategoryEntity entity) {
        return ingredientCategoryRepository.save(entity);
    }

    @Override
    public IngredientCategoryEntity updateIngredientCategory(IngredientCategoryEntity entity) {
        IngredientCategoryEntity result = null;
        Optional<IngredientCategoryEntity> ingredientCategoryEntity = ingredientCategoryRepository.findById(entity.getId());

        if(ingredientCategoryEntity.isPresent()){
            if(ingredientCategoryEntity.get().getTitle().equals("테스트")){
                result = ingredientCategoryRepository.save(entity);
            }
        }
        return result;
    }

    @Override
    public String deleteIngredientCategory(Long id) {
        ingredientCategoryRepository.deleteById(id);
        return "삭제성공";
    }

    @Override
    public List<IngredientCategoryEntity> findByTitle(String title) {
        return ingredientCategoryRepository.findByTitle(title);
    }

    @Override
    public List<IngredientCategoryEntity> findByTitles(String title) {
        return ingredientCategoryRepository.findByTitleContains(title);
    }
}
