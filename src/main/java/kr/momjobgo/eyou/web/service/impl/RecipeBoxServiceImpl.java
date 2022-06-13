package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeRecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeRecipeBoxRepository;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeBoxServiceImpl {

}
//public class RecipeBoxServiceImpl implements RecipeBoxService {
//    private final RecipeBoxRepository recipeBoxRepository;
//    private RecipeRecipeBoxRepository recipeBoxRecipeRepository;
//
//    public RecipeBoxServiceImpl(RecipeBoxRepository RecipeBoxRepository) {
//        this.recipeBoxRepository = RecipeBoxRepository;
//    }
//
//    @Override
//    public List<RecipeBoxEntity> getAll() {
//        return recipeBoxRepository.findAll();
//    }
//
//    @Override
//    public RecipeBoxEntity getRecipeBox(Long id) {
//        Optional<RecipeBoxEntity> recipeBoxEntity= recipeBoxRepository.findById(id);
//        if(recipeBoxEntity.isPresent()) {
//            return recipeBoxEntity.get();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public RecipeBoxEntity insertRecipeBox(String name) {
//        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
//        recipeBoxEntity.setName(name);
//        return recipeBoxRepository.save(recipeBoxEntity);
//    }
//
//    @Override
//    public RecipeBoxEntity insertRecipeBoxEntity(RecipeBoxEntity entity) {
//        return recipeBoxRepository.save(entity);
//    }
//
//    @Override
//    public RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity) {
//        RecipeBoxEntity result = null;
//        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(entity.getId());
////        if(recipeBoxEntity.isPresent()){
////            if(recipeBoxEntity.get().getUserId().equals("")) {
////                result = recipeBoxRepository.save(entity);
////            }
////        }
//        return null;
//    }
//
//    @Override
//    public String deleteRecipeBox(Long id) {
//        recipeBoxRepository.deleteById(id);
//        return "삭제성공";
//    }
//
//    @Override
//    public List<RecipeBoxEntity> findByName(String name) {
//        return recipeBoxRepository.findByName(name);
//    }
//
//    @Override
//    public List<RecipeBoxEntity> findByNameContains(String name) {
//        return recipeBoxRepository.findByNameContains(name);
//    }
//
//    @Override
//    public List<RecipeRecipeBoxEntity> findByRecipeBoxRecipes() {
//        RecipeRecipeBoxEntity result = null;
////        Optional<RecipeRecipeBoxEntity> RecipeBoxRecipeEntity = recipeBoxRepository.findById(entity.getId());
////        if(recipeBoxEntity.isPresent()){
////            if(recipeBoxEntity.get().getUserId().equals("")) {
////                result = recipeBoxRepository.save(entity);
////            }
////        }
//        return null;
//    }
//
//}
