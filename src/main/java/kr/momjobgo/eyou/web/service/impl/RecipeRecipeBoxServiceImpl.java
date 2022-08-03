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
    FileRepository fileRepository;

    public RecipeRecipeBoxServiceImpl(
            RecipeRecipeBoxRepository recipeRecipeBoxRepository,
            RecipeBoxRepository recipeBoxRepository,
            RecipeRepository recipeRepository,
            UserRepository userRepository,
            ScoreRepository scoreRepository,
            TimeTakenRepository timeTakenRepository,
            FileRepository fileRepository) {
                this.recipeRecipeBoxRepository = recipeRecipeBoxRepository;
                this.recipeBoxRepository = recipeBoxRepository;
                this.recipeRepository = recipeRepository;
                this.userRepository = userRepository;
                this.scoreRepository = scoreRepository;
                this.timeTakenRepository = timeTakenRepository;
                this.fileRepository = fileRepository;
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
            recipeRecipeBoxEntity.forEach(rrb -> {
                Map<String, Object> recipe = new HashMap<>();
                Optional<RecipeEntity> recipeEntity = recipeRepository.findById(rrb.getRecipe().getId());
                if(recipeEntity.isPresent()){
                    recipe.put("title", recipeEntity.get().getContentsEntity().getTitle());
                    recipe.put("subTitle", recipeEntity.get().getContentsEntity().getSubTitle());
                    Optional<ScoreEntity> scoreEntity = scoreRepository.findByRecipeId(recipeEntity.get().getId());
                    if(scoreEntity.isPresent()){
                        recipe.put("score", scoreEntity.get().getScore());
                    }
                    Optional<TimeTakenEntity> timeTakenEntity = timeTakenRepository.findById(recipeEntity.get().getTimeTakenId());
                    if(timeTakenEntity.isPresent()){
                        recipe.put("timeTaken", timeTakenEntity.get().getTime());
                    }
                    recipe.put("period", recipeEntity.get().getPeriod());
                    recipe.put("recipeId", recipeEntity.get().getId());
                    recipe.put("contentsId", recipeEntity.get().getContentsId());
                    List<FileEntity> fileEntity = fileRepository.findByContentsId(recipeEntity.get().getContentsId());
                    fileEntity.forEach(file -> {
                        if(file.getFileRealName().startsWith("M")) { // M 으로 시작하는 파일 가져오기
                            recipe.put("fileId", file.getId());
                        }
                    });
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
    public RecipeRecipeBoxEntity insertRecipeBox(Long boxId, Long recipeId) {
        Long userId = UserManager.getUser().getId();
        Optional<RecipeRecipeBoxEntity> findRecipeBox = recipeRecipeBoxRepository.findByRecipeBoxIdAndRecipeIdAndUserId(boxId, recipeId, userId);

        if(findRecipeBox.isPresent()) {
            System.out.println("이미 존재합니다.");
            return findRecipeBox.get();
        }
        else{
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
    }

    @Override
    public RecipeRecipeBoxEntity moveRecipeBox(Long fromBoxId, Long recipeId, Long toBoxId) {
        Long userId = UserManager.getUser().getId();
        Optional<RecipeRecipeBoxEntity> findRecipeBox = recipeRecipeBoxRepository.findByRecipeBoxIdAndRecipeIdAndUserId(fromBoxId, recipeId, userId);
        if(findRecipeBox.isPresent()){
            recipeRecipeBoxRepository.deleteById(findRecipeBox.get().getId());
        }

        RecipeRecipeBoxEntity recipeRecipeBoxEntity = new RecipeRecipeBoxEntity();
        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
        recipeBoxEntity.setId(toBoxId);
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipeId);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        recipeRecipeBoxEntity.setRecipeBox(recipeBoxEntity);
        recipeRecipeBoxEntity.setRecipe(recipeEntity);
        recipeRecipeBoxEntity.setUser(userEntity);
        return recipeRecipeBoxRepository.save(recipeRecipeBoxEntity);
    }

    @Override
    public String deleteRecipeRecipeBox(Long id) {
        if(recipeRecipeBoxRepository.findById(id).isPresent()) {
            recipeRecipeBoxRepository.deleteById(id);
            return "Success to delete ID";
        }
        else {
            return "Fail to delete(Not exist ID)";
        }
    }

    @Override
    public Object deleteRecipe(Long id, Long recipeId) {
        Long userId = UserManager.getUser().getId();
        Optional<RecipeRecipeBoxEntity> findRecipeBox = recipeRecipeBoxRepository.findByRecipeBoxIdAndRecipeIdAndUserId(id, recipeId, userId);
        if(findRecipeBox.isPresent()) {
            recipeRecipeBoxRepository.deleteById(findRecipeBox.get().getId());
            return "Success to delete recipe";
        }
        else {
            return "Fail to delete(Not exist recipe)";
        }
    }

    @Override
    public List<Map<String, Object>> getSubscribeList(Long userId, Long period) {
        return recipeRecipeBoxRepository.getSubscribeList(userId, period);
    }
}
