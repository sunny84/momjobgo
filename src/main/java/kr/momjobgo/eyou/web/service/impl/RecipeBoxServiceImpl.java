package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
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
    public List<RecipeBoxEntity> getAll()  { return recipeBoxRepository.findAll(); }

    @Override
    public RecipeBoxEntity getById(Long id) { return recipeBoxRepository.getById(id); }

    @Override
    public RecipeBoxEntity insertRecipeBoxName(String name) {
        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
        recipeBoxEntity.setName(name);
        recipeBoxEntity.setIsDefault(false);
        return recipeBoxRepository.save(recipeBoxEntity);
    }

    @Override
    public RecipeBoxEntity insertRecipeBox(RecipeBoxEntity entity) {
        entity.setIsDefault(true);
        return recipeBoxRepository.save(entity);
    }

    @Override
    public RecipeBoxEntity updateRecipeBoxNameById(Long id, String name) {
        RecipeBoxEntity result = null;

        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(id);

        if(recipeBoxEntity.isPresent()){
            if(recipeBoxEntity.get().getIsDefault() == null || recipeBoxEntity.get().getIsDefault().equals(false)){
                RecipeBoxEntity entity = new RecipeBoxEntity();
                entity.setName(name);
                result = recipeBoxRepository.save(entity);
            } else {
                System.out.println("기본 레시피 이름을 수정할 수 없음");
            }
        }
        return result;
    }

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
            return "존재하지 않음";
        }
    }

    @Override
    public String deleteRecipeBoxAll() {
        if(recipeBoxRepository.count() > 0 ) {
            recipeBoxRepository.deleteAll();
            return "모두 삭제 성공";
        }
        else {
            return "존재하지 않음";
        }
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
