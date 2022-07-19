package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.jpa.entity.*;
import kr.momjobgo.eyou.web.jpa.repository.*;
import kr.momjobgo.eyou.web.service.RecipeRecipeBoxService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeRecipeBoxServiceImpl implements RecipeRecipeBoxService {
    RecipeRecipeBoxRepository recipeRecipeBoxRepository;
    RecipeBoxRepository recipeBoxRepository;
    RecipeRepository recipeRepository;
    UserRepository userRepository;
    ScoreRepository scoreRepository;
    TimeTakenRepository timeTakenRepository;

    public RecipeRecipeBoxServiceImpl(
            RecipeRecipeBoxRepository recipeRecipeBoxRepository,
            RecipeBoxRepository recipeBoxRepository,
            RecipeRepository recipeRepository,
            UserRepository userRepository,
            ScoreRepository scoreRepository,
            TimeTakenRepository timeTakenRepository) {
                this.recipeRecipeBoxRepository = recipeRecipeBoxRepository;
                this.recipeBoxRepository = recipeBoxRepository;
                this.recipeRepository = recipeRepository;
                this.userRepository = userRepository;
                this.scoreRepository = scoreRepository;
                this.timeTakenRepository = timeTakenRepository;
            }
    @Override
    public List<RecipeRecipeBoxEntity> getAll() { return recipeRecipeBoxRepository.findAll(); }

    @Override
    public List<RecipeRecipeBoxEntity> getRecipeRecipeBoxByUserId(){
        List<RecipeRecipeBoxEntity> recipeRecipeBoxEntity = recipeRecipeBoxRepository.findByUserId(UserManager.getUser().getId());

        if(!recipeRecipeBoxEntity.isEmpty()){
            return recipeRecipeBoxEntity;
        } else {
            return null;
        }
    }

    @Override
    public RecipeRecipeBoxEntity getRecipeRecipeBox(Long id){

        Optional<RecipeRecipeBoxEntity> recipeRecipeBoxEntity = recipeRecipeBoxRepository.findById(id);

        if(recipeRecipeBoxEntity.isPresent()){
            return recipeRecipeBoxEntity.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findByRecipeContents(Long boxId) {
        List recipes = new ArrayList<>();
        List<RecipeRecipeBoxEntity> recipeRecipeBoxEntity = recipeRecipeBoxRepository.findByRecipeBoxIdAndUserId(boxId, UserManager.getUser().getId());
        if(!recipeRecipeBoxEntity.isEmpty()){
            recipeRecipeBoxEntity.forEach(item -> {
                Map<String, Object> recipe = new HashMap<>();
                Optional<RecipeEntity> recipeEntity = recipeRepository.findById(item.getRecipe().getId());
                if(recipeEntity.isPresent()){
                    recipe.put("title", recipeEntity.get().getContentsEntity().getTitle());
                    recipe.put("subTitle", recipeEntity.get().getContentsEntity().getSubTitle());
                    recipe.put("score", scoreRepository.findByRecipeId(recipeEntity.get().getId()).get().getScore());
                    recipe.put("timeTaken", timeTakenRepository.findById(recipeEntity.get().getTimeTakenId()).get().getTime());
                    recipe.put("period", recipeEntity.get().getPeriod());
                    recipe.put("recipeId", recipeEntity.get().getId());
                }
                recipes.add(recipe);
            });
        }
//        System.out.println(recipes);
        return recipes;
    }

    @Override
    public List<RecipeRecipeBoxEntity> findByRecipeBoxId(Long boxId){
        return recipeRecipeBoxRepository.findByRecipeBoxId(boxId);
    }

    @Override
    public List<RecipeRecipeBoxEntity> findRecipesByRecipeBoxId(Long boxId){
        return recipeRecipeBoxRepository.findRecipesByRecipeBoxId(boxId);
    }

    @Override
    public List<RecipeRecipeBoxEntity> findByRecipeBoxIdAndRecipeIdAndUserId(Long boxId, Long recipeId, Long userId) {
        return recipeRecipeBoxRepository.findByRecipeBoxIdAndRecipeIdAndUserId(boxId, recipeId, userId);
    }

    @Override
    public RecipeRecipeBoxEntity insertRecipeBox(Long boxId, Long recipeId) {
        Long userId = UserManager.getUser().getId();
        List<RecipeRecipeBoxEntity> findRecipeBox = recipeRecipeBoxRepository.findByRecipeBoxIdAndRecipeIdAndUserId(boxId, recipeId, userId);
        if(findRecipeBox.isEmpty()){
            RecipeRecipeBoxEntity recipeRecipeBoxEntity = new RecipeRecipeBoxEntity();

            RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
            recipeBoxEntity.setId(boxId);
            RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.setId(recipeId);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);

            recipeRecipeBoxEntity.setRecipeBox(recipeBoxEntity);
            recipeRecipeBoxEntity.setRecipe(recipeEntity);
            recipeRecipeBoxEntity.setUser(userEntity);
            return recipeRecipeBoxRepository.save(recipeRecipeBoxEntity);
        }
        else{
//            System.out.println("이미 존재합니다.");
            return findRecipeBox.get(0);
        }
    }

    @Override
    public String deleteRecipeRecipeBox(Long id) {
        if(recipeRecipeBoxRepository.findById(id).isPresent()) {
            recipeRecipeBoxRepository.deleteById(id);
            return "삭제성공";
        }
        else {
            return "아이디가 존재하지 않음";
        }
    }
}
