package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeRepository;
import kr.momjobgo.eyou.web.service.RecipeService;

import java.util.List;
import java.util.Optional;
public class RecipeServiceImpl {

}
//
//public class RecipeServiceImpl implements RecipeService {
//    private RecipeRepository recipeRepository;
//
//    public RecipeServiceImpl(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }
//
//    @Override
//    public List<RecipeEntity> getAll() {
//        return recipeRepository.findAll();
//    }
//
//    @Override
//    public RecipeEntity getRecipe(Long id) {
//        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);
//        if(recipeEntity.isPresent()) {
//            return recipeEntity.get();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public RecipeEntity insertRecipeEntity(RecipeEntity entity) {
//        return recipeRepository.save(entity);
//    }
//
//    @Override
//    public RecipeEntity updateRecipe(RecipeEntity entity) {
//        RecipeEntity result = null;
//        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(entity.getId());
//        if(recipeEntity.isPresent()){
//            // 조건 처리
//            result = recipeRepository.save(entity);
//        }
//        return result;
//    }
//
//    @Override
//    public String deleteRecipe(Long id) {
//        recipeRepository.deleteById(id);
//        return "삭제성공";
//    }
//}
